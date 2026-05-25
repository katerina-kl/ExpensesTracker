package com.example.expensestracker.feature.expenses.list;

import androidx.lifecycle.SavedStateHandle;
import com.example.expensestracker.domain.usecase.ExpenseUseCases;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ExpensesViewModel_Factory implements Factory<ExpensesViewModel> {
  private final Provider<ExpenseUseCases> expenseUseCasesProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ExpensesViewModel_Factory(Provider<ExpenseUseCases> expenseUseCasesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.expenseUseCasesProvider = expenseUseCasesProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ExpensesViewModel get() {
    return newInstance(expenseUseCasesProvider.get(), savedStateHandleProvider.get());
  }

  public static ExpensesViewModel_Factory create(Provider<ExpenseUseCases> expenseUseCasesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ExpensesViewModel_Factory(expenseUseCasesProvider, savedStateHandleProvider);
  }

  public static ExpensesViewModel newInstance(ExpenseUseCases expenseUseCases,
      SavedStateHandle savedStateHandle) {
    return new ExpensesViewModel(expenseUseCases, savedStateHandle);
  }
}
