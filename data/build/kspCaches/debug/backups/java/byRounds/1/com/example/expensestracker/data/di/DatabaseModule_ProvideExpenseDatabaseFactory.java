package com.example.expensestracker.data.di;

import android.app.Application;
import com.example.expensestracker.data.local.ExpenseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class DatabaseModule_ProvideExpenseDatabaseFactory implements Factory<ExpenseDatabase> {
  private final Provider<Application> appProvider;

  public DatabaseModule_ProvideExpenseDatabaseFactory(Provider<Application> appProvider) {
    this.appProvider = appProvider;
  }

  @Override
  public ExpenseDatabase get() {
    return provideExpenseDatabase(appProvider.get());
  }

  public static DatabaseModule_ProvideExpenseDatabaseFactory create(
      Provider<Application> appProvider) {
    return new DatabaseModule_ProvideExpenseDatabaseFactory(appProvider);
  }

  public static ExpenseDatabase provideExpenseDatabase(Application app) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideExpenseDatabase(app));
  }
}
