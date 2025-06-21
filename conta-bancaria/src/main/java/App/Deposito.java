package App;

import java.math.BigDecimal;

public class Deposito {
    public static void realizar(ContaCliente conta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            conta.setSaldo(conta.getSaldo().add(valor));
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }
}

