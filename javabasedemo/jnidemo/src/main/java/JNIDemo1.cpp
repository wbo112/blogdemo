#include "com_wbo112_jni_JNIDemo.h"
#include <iostream>
#include <thread>
#include <cstdlib>
#include <ctime> 
#include <unistd.h>

JavaVM *vm=NULL;

unsigned seed;


//在java加载so文件的时候，就会调用到这个JNI_OnLoad
//oracle文档这里有介绍：https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#JNJI_OnLoad
jint JNI_OnLoad(JavaVM *jvm, void *reserved){
	//这个表示java虚拟机,这个需要保存下来，因为JNIEnv只是当前线程有效，如果要在其他线程获取JNIEnv,就需要通过这个jvm来获取，后面有相应代码
	vm=jvm;

	//这个是为了模拟后面的回调时间，业务中应该不关注
	seed = time(0);
    srand(seed);

	return JNI_VERSION_1_8;

}

//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#JNI_OnUnload
//so文件卸载的时候，会执行这个函数，在这里面可以做一些清理工作
void JNI_OnUnload_L(JavaVM *vm, void *reserved){
	vm=NULL;
}


void threadfunc(jobject jobj,jstring jstr)
{	
	JNIEnv*	env = NULL;
	sleep(rand() % 10);

	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html#GetEnv
	//这里有介绍，如果不是当前线程，是获取不到env的，这时，返回值就是JNI_EDETACHED,这时就需要调用AttachCurrentThread，给当前线程绑定env
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
	

	//这个是将jstring转成char*
	const char *str = env->GetStringUTFChars(jstr, 0);
			
    std::cout << "start process task " + (std::string)str << std::endl;
	
	//通过对象获取对应的类
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetObjectClass
	jclass jcls=env->GetObjectClass(jobj);

	//另一种方式获取对应的类
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#FindClass
	jclass jentrycls=env->FindClass("com/wbo112/jni/JNIDemo$Entry");

	//获取无参的构造方法
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetMethodID
	jmethodID  jinit=env->GetMethodID(jcls,"<init>", "()V");

	//调用构造方法获取对象
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#NewObject
	jobject jentryobj=env->NewObject(jentrycls,jinit);

	//获取entry类的str字段
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetFieldID
	jfieldID jfieldStr=env->GetFieldID(jentrycls,"str","Ljava/lang/String;");

	//给str字段设置值
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#Set_type_Field_routines
	env->SetObjectField(jentryobj,jfieldStr,jstr);
	char* msg = "Hello World!";
    jstring result = env->NewStringUTF(msg);

	jfieldID jfieldResult=env->GetFieldID(jentrycls,"result","Ljava/lang/String;");
	
	env->SetObjectField(jentryobj,jfieldResult,result);
	
	//获取方法
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetMethodID
	jmethodID jmtd=env->GetMethodID(jcls,"putEntry", "(Lcom/wbo112/jni/JNIDemo$Entry;)V");
	
	//调用方法
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#Call_type_Method_routines
	env->CallVoidMethod(jobj,jmtd,jentryobj);
	std::cout<<" end process task  " + (std::string)str<<std::endl;
	//释放前面构造的char* 
	env->ReleaseStringUTFChars( jstr, str);

	//释放全局对象
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#DeleteGlobalRef
	env->DeleteGlobalRef(jobj);
	env->DeleteGlobalRef(jstr);

	//当前线程和jvm进行分离
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

	//jobject对象是不能跨线程传递的，需要先转成全局引用
	//https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#NewGlobalRef
	jobject globalJobj=env->NewGlobalRef(jobj);
	jobject globalJstr=env->NewGlobalRef(jstr);
	
	//这里使用的是std::thread ,所以编译的时候需要加参数-std=c++11
   	std::thread t1(threadfunc,globalJobj,(jstring)globalJstr);
    //t1.join();
    t1.detach();
	return 1;




}
