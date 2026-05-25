package com.example.expensestracker.feature.expenses.addedit;

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
public final class AddEditExpenseViewModel_Factory implements Factory<AddEditExpenseViewModel> {
  private final Provider<ExpenseUseCases> expenseUseCasesProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public AddEditExpenseViewModel_Factory(Provider<ExpenseUseCases> expenseUseCasesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.expenseUseCasesProvider = expenseUseCasesProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public AddEditExpenseViewModel get() {
    return newInstance(expenseUseCasesProvider.get(), savedStateHandleProvider.get());
  }

  public static AddEditExpenseViewModel_Factory create(
      Provider<ExpenseUseCases> expenseUseCasesProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new AddEditExpenseViewModel_Factory(expenseUseCasesProvider, savedStateHandleProvider);
  }

  public static AddEditExpenseViewModel newInstance(ExpenseUseCases expenseUseCases,
      SavedStateHandle savedStateHandle) {
    return new AddEditExpenseViewModel(expenseUseCases, savedStateHandle);
  }
}
