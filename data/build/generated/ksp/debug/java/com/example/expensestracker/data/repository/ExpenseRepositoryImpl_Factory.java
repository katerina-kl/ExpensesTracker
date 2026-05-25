package com.example.expensestracker.data.repository;

import com.example.expensestracker.data.local.ExpenseDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.example.expensestracker.core.common.dispatcher.IoDispatcher")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class ExpenseRepositoryImpl_Factory implements Factory<ExpenseRepositoryImpl> {
  private final Provider<ExpenseDao> daoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public ExpenseRepositoryImpl_Factory(Provider<ExpenseDao> daoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.daoProvider = daoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public ExpenseRepositoryImpl get() {
    return newInstance(daoProvider.get(), ioDispatcherProvider.get());
  }

  public static ExpenseRepositoryImpl_Factory create(Provider<ExpenseDao> daoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new ExpenseRepositoryImpl_Factory(daoProvider, ioDispatcherProvider);
  }

  public static ExpenseRepositoryImpl newInstance(ExpenseDao dao,
      CoroutineDispatcher ioDispatcher) {
    return new ExpenseRepositoryImpl(dao, ioDispatcher);
  }
}
