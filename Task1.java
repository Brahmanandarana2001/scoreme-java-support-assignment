import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanAccountService {

    public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {

        // FIX: Initialize result list to avoid NullPointerException
        List<LoanAccount> result = new ArrayList<>();

        // FIX: Prevent NullPointerException if accounts list itself is null
        if (accounts == null) {
            return result;
        }

        for (LoanAccount account : accounts) {

            // FIX: dueDate may be null for restructured accounts
            if (account.getDueDate() != null &&
                    account.getDueDate().before(new Date())) {

                // FIX: only overdue accounts with positive balance should be added
                if (account.getOutstandingBalance() > 0) {
                    result.add(account);
                }
            }
        }

        return result;
    }
}

// LoanAccount fields:
// Date dueDate          — may be null for restructured accounts
// double outstandingBalance
// String accountId      — always non-null
