#include "com_wbo112_jni_JNIDemo.h"
#include <iostream>
#include <thread>
#include <cstdlib>
#include <ctime> 
#include <unistd.h>

JavaVM *vm=NULL;

unsigned seed;


//��java����so�ļ���ʱ�򣬾ͻ���õ����JNI_OnLoad
//oracle�ĵ������н��ܣ�https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#JNJI_OnLoad
jint JNI_OnLoad(JavaVM *jvm, void *reserved){
	//�����ʾjava�����,�����Ҫ������������ΪJNIEnvֻ�ǵ�ǰ�߳���Ч�����Ҫ�������̻߳�ȡJNIEnv,����Ҫͨ�����jvm����ȡ����������Ӧ����
	vm=jvm;

	//�����Ϊ��ģ�����Ļص�ʱ�䣬ҵ����Ӧ�ò���ע
	seed = time(0);
    srand(seed);

	return JNI_VERSION_1_8;

}

//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#JNI_OnUnload
//so�ļ�ж�ص�ʱ�򣬻�ִ������������������������һЩ������
void JNI_OnUnload_L(JavaVM *vm, void *reserved){
	vm=NULL;
}


void threadfunc(jobject jobj,jstring jstr)
{	
	JNIEnv*	env = NULL;
	sleep(rand() % 10);

	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#GetEnv
	//�����н��ܣ�������ǵ�ǰ�̣߳��ǻ�ȡ����env�ģ���ʱ������ֵ����JNI_EDETACHED,��ʱ����Ҫ����AttachCurrentThread������ǰ�̰߳�env
	jint status = vm->GetEnv((void **)&env, JNI_VERSION_1_8);

    	if (status == JNI_EDETACHED) {   		
			//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#AttachCurrentThread
			if (vm->AttachCurrentThread((void **)&env, NULL)!= JNI_OK){
				return;
			}
		
		} else if(status!= JNI_OK){
		std::cout<<"getEnv err"<<std::endl;
		return;
	
	}
	

	//����ǽ�jstringת��char*
	const char *str = env->GetStringUTFChars(jstr, 0);
			
    std::cout << "start process task " + (std::string)str << std::endl;
	
	//ͨ�������ȡ��Ӧ����
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetObjectClass
	jclass jcls=env->GetObjectClass(jobj);

	//��һ�ַ�ʽ��ȡ��Ӧ����
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#FindClass
	jclass jentrycls=env->FindClass("com/wbo112/jni/JNIDemo$Entry");

	//��ȡ�޲εĹ��췽��
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetMethodID
	jmethodID  jinit=env->GetMethodID(jcls,"<init>", "()V");

	//���ù��췽����ȡ����
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#NewObject
	jobject jentryobj=env->NewObject(jentrycls,jinit);

	//��ȡentry���str�ֶ�
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetFieldID
	jfieldID jfieldStr=env->GetFieldID(jentrycls,"str","Ljava/lang/String;");

	//��str�ֶ�����ֵ
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#Set_type_Field_routines
	env->SetObjectField(jentryobj,jfieldStr,jstr);
	char* msg = "Hello World!";
    jstring result = env->NewStringUTF(msg);

	jfieldID jfieldResult=env->GetFieldID(jentrycls,"result","Ljava/lang/String;");
	
	env->SetObjectField(jentryobj,jfieldResult,result);
	
	//��ȡ����
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetMethodID
	jmethodID jmtd=env->GetMethodID(jcls,"putEntry", "(Lcom/wbo112/jni/JNIDemo$Entry;)V");
	
	//���÷���
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#Call_type_Method_routines
	env->CallVoidMethod(jobj,jmtd,jentryobj);
	std::cout<<" end process task  " + (std::string)str<<std::endl;
	//�ͷ�ǰ�湹���char* 
	env->ReleaseStringUTFChars( jstr, str);

	//�ͷ�ȫ�ֶ���
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#DeleteGlobalRef
	env->DeleteGlobalRef(jobj);
	env->DeleteGlobalRef(jstr);

	//��ǰ�̺߳�jvm���з���
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#DetachCurrentThread
	vm->DetachCurrentThread();
}	

JNIEXPORT jstring JNICALL Java_com_wbo112_jni_JNIDemo_callHello
  (JNIEnv *env, jobject jobj){
	char* msg = "Hello World!";
	jstring result = env->NewStringUTF(msg); // C style string to Java String
 	return result;


}
JNIEXPORT jboolean JNICALL Java_com_wbo112_jni_JNIDemo_sendMsg
  (JNIEnv *env, jobject jobj, jstring jstr){

	//jobject�����ǲ��ܿ��̴߳��ݵģ���Ҫ��ת��ȫ������
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#NewGlobalRef
	jobject globalJobj=env->NewGlobalRef(jobj);
	jobject globalJstr=env->NewGlobalRef(jstr);
	
	//����ʹ�õ���std::thread ,���Ա����ʱ����Ҫ�Ӳ���-std=c++11
   	std::thread t1(threadfunc,globalJobj,(jstring)globalJstr);
    //t1.join();
    t1.detach();
	return 1;




}
