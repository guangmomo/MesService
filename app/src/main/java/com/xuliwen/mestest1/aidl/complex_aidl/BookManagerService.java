package com.xuliwen.mestest1.aidl.complex_aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.xuliwen.mestest1.aidl.Book;
import com.xuliwen.mestest1.aidl.IBookManager;
import com.xuliwen.mestest1.aidl.IOnNewBookArrivedListener;
import com.xuliwen.mestest1.utils.L;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {

    private static final String TAG="BookManagerService  ";
    private CopyOnWriteArrayList<Book> bookList=new CopyOnWriteArrayList<>();
   // private CopyOnWriteArrayList<IOnNewBookArrivedListener> listenerList=new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> listenerList=new RemoteCallbackList<>();

    private AtomicBoolean isServiceDestroy=new AtomicBoolean(false);

    public BookManagerService() {
    }
    
    private IBookManager.Stub binder=new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listenerList.register(listener);
            int size=listenerList.beginBroadcast();
            listenerList.finishBroadcast();
            L.l(TAG+"listener的数量为： "+size);

            /*if(!listenerList.contains(listener)){
                    listenerList.add(listener);
                    L.l(TAG+"listener注册成功");
                }else{
                    L.l(TAG+"listener已经注册过了，请不要重复注册");
                }*/

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            listenerList.unregister(listener);
            int size=listenerList.beginBroadcast();
            listenerList.finishBroadcast();
            L.l(TAG+"listener的数量为： "+size);

            /* if(listenerList.contains(listener)) {
                listenerList.remove(listener);
                L.l(TAG+"listener解注册成功");
            }else{
                L.l(TAG+"listener解注册失败： 未找到listener");
            }*/
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bookList.add(new Book("第一行代码"));
        bookList.add(new Book("疯狂安卓讲义"));
        addBookThread.start();
    }

    /**
     * 如果Service和Activity在同一个进程，那么onBind是在UIThread中执行的
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        int check=checkCallingOrSelfPermission("com.xuliwen.mestest1.ACCESS_BOOK_SERVICE");
        if(check== PackageManager.PERMISSION_DENIED){
            return null;
        }
       //; L.l("MesService BookManagerService onBind ThreadId :"+Thread.currentThread().getId());
        return binder;
    }


    private Thread addBookThread=new Thread(){
        @Override
        public void run() {
            int i=0;
            while (!isServiceDestroy.get()){
                Book book=new Book("新书"+(i++));
                bookList.add(book);
                onNewBookArrived(book);
                try {
                    Thread.sleep(2*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private void onNewBookArrived(Book book){
        int size=listenerList.beginBroadcast();
        for(int i=0;i<size;i++){
            IOnNewBookArrivedListener listener= listenerList.getBroadcastItem(i);
            if(listener!=null){
                try {
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listenerList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceDestroy.set(true);
    }
}
