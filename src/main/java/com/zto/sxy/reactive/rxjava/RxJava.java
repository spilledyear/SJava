package com.zto.sxy.reactive.rxjava;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import static io.reactivex.BackpressureStrategy.BUFFER;
import static io.reactivex.BackpressureStrategy.DROP;

/**
 * @author spilledyear
 * @date 2019-01-19 13:45
 */
@Slf4j
public class RxJava {


    /**
     * 最基本的使用方式
     */
    @Test
    public void test01() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> observableEmitter) throws Exception {
                log.info("可以在这里触发消费者的方法");

                observableEmitter.onNext("onNext方法被调用");
                observableEmitter.onComplete();
            }
        }).subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable disposable) {
                log.info("Observable调用subscribe方法时会触发这个onSubscribe方法");
            }

            @Override
            public void onNext(Object o) {
                log.info(o.toString());
            }

            @Override
            public void onError(Throwable throwable) {
                log.info("onError方法被调用");
            }

            @Override
            public void onComplete() {
                log.info("onComplete方法被调用");
            }
        });
    }


    /**
     * 简便版
     */
    @Test
    public void test02() {
        Observable.just("呵呵").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                log.info(s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                log.info("onComplete方法被调用");
            }
        });
    }


    /**
     * 背压版
     */
    @Test
    public void test03() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                if (!emitter.isCancelled()) {
                    emitter.onNext("onNext 1");
                    emitter.onNext("onNext 2");
                    emitter.onNext("onNext 3");
                    emitter.onComplete();
                }
            }
        }, DROP).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(2L);
                log.info("背压订阅");
            }

            @Override
            public void onNext(String s) {
                log.info(s);
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }


    /**
     * map 操作符
     */
    @Test
    public void test04() {
        Flowable.create(emitter -> emitter.onNext("onNext 1"), DROP)
                .map(v -> v + " MAP")
                .subscribe(System.out::println);
    }


    /**
     * 线程切换
     */
    @Test
    public void test05() throws InterruptedException {
        Flowable.create(emitter -> {
            log.info("发射数据的线程 => {}", Thread.currentThread().getName());
            emitter.onNext("DD");
        }, BUFFER)

                // 指定在哪个线程上发射数据
                .subscribeOn(Schedulers.io())

                // 指定接收数据后在哪个线程上执行
                .observeOn(Schedulers.newThread())

                .subscribe(new Subscriber<Object>() {
                               @Override
                               public void onSubscribe(Subscription subscription) {
                                   log.info("onSubscribe  Thread => {}", Thread.currentThread().getName());
                               }

                               @Override
                               public void onNext(Object s) {
                                   log.info("onNext  Data => {},  Thread => {}", s, Thread.currentThread().getName());
                               }

                               @Override
                               public void onError(Throwable throwable) {
                                   log.info("onError  Thread => {}", Thread.currentThread().getName());
                               }

                               @Override
                               public void onComplete() {
                                   log.info("onComplete  Thread => {}", Thread.currentThread().getName());
                               }
                           }
                );

        Thread.sleep(3000);
    }
}
