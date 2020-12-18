package com.malec.netCrackerLab.di;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

public class TestModule {
    @Provides
    A a() {
        return new A();
    }

    @Provides
    @Singleton
    List<A> listA() {
        A a1 = new A();
        A a2 = new A();
        return Arrays.asList(a1, a2);
    }

    @Provides
    @Singleton
    List<Set<A>> listSetA() {
        A a1 = new A();
        A a2 = new A();
        return Collections.singletonList(Set.of(a1, a2));
    }

    @Provides
    @Singleton
    B b(A a) {
        return new B(a);
    }

    @Provides
    @Singleton
    C c(A a, B b) {
        return new C(a, b);
    }

    public static class A {
        private static int count = 0;

        public final int a = count++;
    }

    public static class B {
        public final A a;

        public B(A a) {
            this.a = a;
        }
    }

    public static class C {
        public final A a;
        public final B b;

        public C(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class D {
        @Inject
        public A a;
        @Inject
        public B b;
        @Inject
        public C c;
        @Inject
        public List<A> listA;
        @Inject
        public List<Set<A>> listSetA;
    }
}
