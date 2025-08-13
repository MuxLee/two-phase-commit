package net.mux.twophasecommit.listener.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;

public interface BeanProvider<T> {

    T getBean(@NonNull final ApplicationContext applicationContext);

}