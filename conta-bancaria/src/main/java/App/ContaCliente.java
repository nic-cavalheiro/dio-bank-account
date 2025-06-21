package App;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class ContaCliente {

    private String nome;
    private String agencia;
    private String conta;
    private BigDecimal saldo;

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getAgencia(String nome) {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return String.format(
                "| Conta: %-6s | Agência: %-4s | Cliente: %-20s | Saldo: %12s |",
                conta, agencia, nome, nf.format(saldo)
        );
    }
}
