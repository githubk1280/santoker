package com.rxjava;

import org.springframework.beans.BeanUtils;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lefu on 15/8/17.
 * Description:
 */
public class Learning1 {
    public static void main(String args[]) {
        String names[] = new String[]{"james", "jack", "someone1", "rose", "Shelly", "Sumrise"};
        List<O> oList = new ArrayList<O>();
        oList.add(new O("JAMES", "v1"));
        oList.add(new O("JAMES1", "v11"));
        oList.add(new O("JAMES2", "v12"));
        oList.add(new O("JAMES3", "v13"));
//        hello(names);
//        toInteger(names);
//        flatMap(names);
        flatMapList(oList);
    }


    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("---" + s);
            }
        });
    }

    public static void toInteger(String... names) {
        Observable.from(names).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.length();
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
                throw new RuntimeException("error throw");
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("error---" + throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("done");
            }
        });
    }

    public static void flatMapList(List<O> oList) {
        final List<V> vList = null;
        System.out.println("flapMap=======================");
        Observable.from(oList).flatMap(new Func1<O, Observable<V>>() {
            @Override
            public Observable<V> call(O o) {
                V v = new V();
                BeanUtils.copyProperties(o, v);
                return Observable.from(Arrays.asList(v));
            }
        }).subscribe(new Action1<V>() {
            @Override
            public void call(V v) {
                vList.add(v);
                System.out.println(v.toString());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("throwable ="+throwable.getMessage());
            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });
        System.out.println("list = "+vList);
    }

    public static void flatMap(String... names) {
        System.out.println("flapMap=======================");
        Observable.from(names).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return Observable.from(Arrays.asList(s));
            }
        }).filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return s.length() > 4;
            }
        }).take(2).doOnNext(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.print(s + "---");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }


    static class O {
        private String name;
        private String value;

        public O(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "O{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    static class V {
        private String name;
        private String value;

        public V(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public V() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "V{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
