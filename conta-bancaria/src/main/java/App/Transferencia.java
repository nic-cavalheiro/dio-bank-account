package App;

import java.math.BigDecimal;

public class Transferencia {
    public static void realizar(ContaCliente origem, ContaCliente destino, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor inválido para transferência.");
        } else if (origem.getSaldo().compareTo(valor) >= 0) {
            origem.setSaldo(origem.getSaldo().subtract(valor));
            destino.setSaldo(destino.getSaldo().add(valor));
            System.out.println("Transferência realizada com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para transferência.");
        }
    }
}
