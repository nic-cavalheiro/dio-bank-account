package App.WebSystem;

import App.ContaCliente;
import App.Deposito;
import App.Saque;
import App.Transferencia;

import java.math.BigDecimal;
import java.util.*;

public class BankSystem {
    private static final List<ContaCliente> contas = new ArrayList<>();
    private static int contadorContas = 1001;
    private static final String AGENCIA_PADRAO = "0001";

    private static final Map<String, String> estadoUsuario = new HashMap<>();

    public static String executeCommand(String input) {
        input = input.trim();
        switch (input) {
            case "menu" -> {
                return exibirMenu();
            }
            case "1" -> {
                estadoUsuario.put("estado", "criando_conta_nome");
                return "👤 Digite o nome do cliente:";
            }
            case "2" -> {
                estadoUsuario.put("estado", "deposito_conta");
                return "🏦 Digite o número da conta para depósito:";
            }
            case "3" -> {
                estadoUsuario.put("estado", "saque_conta");
                return "💸 Digite o número da conta para saque:";
            }
            case "4" -> {
                estadoUsuario.put("estado", "transferencia_origem");
                return "🔄 Digite o número da conta de origem:";
            }
            case "5" -> {
                return listarContas();
            }
            case "0" -> {
                return "👋 Encerrando sessão.";
            }
            default -> {
                return processarEstado(input);
            }
        }
    }

    private static String exibirMenu() {
        return """
                🏦 MENU BANCÁRIO INTERATIVO:
                1. Criar conta
                2. Depositar
                3. Sacar
                4. Transferir
                5. Listar contas
                0. Sair
                """;
    }

    private static String processarEstado(String input) {
        String estado = estadoUsuario.getOrDefault("estado", "");
        estadoUsuario.remove("estado");

        switch (estado) {
            case "criando_conta_nome" -> {
                ContaCliente conta = new ContaCliente();
                conta.setNome(input);
                conta.setAgencia(AGENCIA_PADRAO);
                conta.setConta(String.valueOf(contadorContas++));
                conta.setSaldo(BigDecimal.ZERO);
                contas.add(conta);

                return """
                        ✅ Conta criada com sucesso!
                        """ +
                        "Olá " + conta.getNome() + ", obrigado por criar uma conta em nosso banco, sua agência é "
                        + conta.getAgencia(conta.getNome()) + " e seu saldo de " + conta.getSaldo() +
                        "$ já está disponível para saque!\n\n" + formatarConta(conta);
            }

            case "deposito_conta" -> {
                ContaCliente conta = buscarConta(input);
                if (conta == null) return "❌ Conta não encontrada.";
                estadoUsuario.put("estado", "deposito_valor");
                estadoUsuario.put("conta_temp", input);
                return "Digite o valor a depositar:";
            }

            case "deposito_valor" -> {
                ContaCliente conta = buscarConta(estadoUsuario.get("conta_temp"));
                BigDecimal valor = new BigDecimal(input);
                Deposito.realizar(conta, valor);
                return "✅ Depósito de R$" + valor + " realizado com sucesso!\nSaldo atual: R$" + conta.getSaldo();
            }

            case "saque_conta" -> {
                ContaCliente conta = buscarConta(input);
                if (conta == null) return "❌ Conta não encontrada.";
                estadoUsuario.put("estado", "saque_valor");
                estadoUsuario.put("conta_temp", input);
                return "Digite o valor a sacar:";
            }

            case "saque_valor" -> {
                ContaCliente conta = buscarConta(estadoUsuario.get("conta_temp"));
                BigDecimal valor = new BigDecimal(input);
                Saque.realizar(conta, valor);
                return "✅ Saque de R$" + valor + " realizado!\nSaldo atual: R$" + conta.getSaldo();
            }

            case "transferencia_origem" -> {
                ContaCliente conta = buscarConta(input);
                if (conta == null) return "❌ Conta de origem não encontrada.";
                estadoUsuario.put("estado", "transferencia_destino");
                estadoUsuario.put("origem", input);
                return "Digite o número da conta de destino:";
            }

            case "transferencia_destino" -> {
                ContaCliente conta = buscarConta(input);
                if (conta == null) return "❌ Conta de destino não encontrada.";
                estadoUsuario.put("estado", "transferencia_valor");
                estadoUsuario.put("destino", input);
                return "Digite o valor da transferência:";
            }

            case "transferencia_valor" -> {
                ContaCliente origem = buscarConta(estadoUsuario.get("origem"));
                ContaCliente destino = buscarConta(estadoUsuario.get("destino"));
                BigDecimal valor = new BigDecimal(input);
                Transferencia.realizar(origem, destino, valor);
                return "✅ Transferência de R$" + valor + " concluída.";
            }

            default -> {
                return "❌ Comando inválido ou fora de contexto. Digite 'menu' para ver as opções.";
            }
        }
    }

    private static ContaCliente buscarConta(String numero) {
        for (ContaCliente c : contas) {
            if (c.getConta().equals(numero)) {
                return c;
            }
        }
        return null;
    }

    private static String listarContas() {
        if (contas.isEmpty()) return "⚠️ Nenhuma conta cadastrada.";
        StringBuilder sb = new StringBuilder("📄 Contas cadastradas:\n");
        for (ContaCliente c : contas) {
            sb.append(formatarConta(c)).append("\n");
        }
        return sb.toString();
    }

    private static String formatarConta(ContaCliente c) {
        return String.format("Conta: %s | Agência: %s | Cliente: %s | Saldo: R$ %.2f",
                c.getConta(), c.getAgencia(c.getNome()), c.getNome(), c.getSaldo());
    }
}
