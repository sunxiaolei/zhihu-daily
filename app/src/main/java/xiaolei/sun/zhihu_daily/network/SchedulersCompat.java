package xiaolei.sun.zhihu_daily.network;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SchedulersCompat {

    private final static Observable.Transformer ioTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object o) {
            return ((Observable) o).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> Observable.Transformer<T, T> applyIoSchedulers() {
        return (Observable.Transformer<T, T>) ioTransformer;
    }
}
