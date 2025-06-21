package App;

import java.math.BigDecimal;
import java.util.*;

public class ContaTerminal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<ContaCliente> contas = new ArrayList<>();
    private static int contadorContas = 1001; // número inicial de conta
    private static final String AGENCIA_PADRAO = "0001";

    public static void main(String[] args) {
        String opcao = "";
        while (!opcao.equals("0")) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextLine();

            limparTela();
            switch (opcao) {
                case "1" -> criarConta();
                case "2" -> deposito();
                case "3" -> saque();
                case "4" -> transferencia();
                case "5" -> listarContas();
                case "0" -> System.out.println("Encerrando...");
                default -> {
                    System.out.println("❌ Opção inválida.");
                    pausar();
                }
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1. Criar conta");
        System.out.println("2. Depositar");
        System.out.println("3. Sacar");
        System.out.println("4. Transferir");
        System.out.println("5. Listar contas");
        System.out.println("0. Sair");
    }

    private static void criarConta() {
        ContaCliente conta = new ContaCliente();

        System.out.print("Nome do cliente: ");
        conta.setNome(scanner.nextLine());

        conta.setAgencia(AGENCIA_PADRAO);
        conta.setConta(gerarNumeroConta());
        conta.setSaldo(BigDecimal.ZERO);

        contas.add(conta);

        System.out.println("Olá " + conta.getNome() + ", obrigado por criar uma conta em nosso banco, sua agência é "
                + conta.getAgencia(conta.getNome()) + " e seu saldo de " + conta.getSaldo() +
                "$ já está disponível para saque!");

        System.out.println(conta);
        pausar();
    }

    private static String gerarNumeroConta() {
        return String.valueOf(contadorContas++);
    }

    private static ContaCliente buscarConta() {
        System.out.print("Digite o número da conta: ");
        String numero = scanner.nextLine();
        for (ContaCliente c : contas) {
            if (c.getConta().equals(numero)) {
                return c;
            }
        }
        System.out.println("Conta não encontrada.");
        return null;
    }

    private static void deposito() {
        ContaCliente conta = buscarConta();
        if (conta == null) return;

        System.out.print("Valor do depósito: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());
        Deposito.realizar(conta, valor);
        pausar();
    }

    private static void saque() {
        ContaCliente conta = buscarConta();
        if (conta == null) return;

        System.out.print("Valor do saque: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());
        Saque.realizar(conta, valor);
        pausar();
    }

    private static void transferencia() {
        System.out.println("Conta de origem:");
        ContaCliente origem = buscarConta();
        if (origem == null) return;

        System.out.println("Conta de destino:");
        ContaCliente destino = buscarConta();
        if (destino == null || origem == destino) {
            System.out.println("Conta de destino inválida.");
            return;
        }

        System.out.print("Valor da transferência: ");
        BigDecimal valor = new BigDecimal(scanner.nextLine());
        Transferencia.realizar(origem, destino, valor);
        pausar();
    }

    private static void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("⚠️ Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("========== CONTAS CADASTRADAS ==========");
        for (ContaCliente c : contas) {
            System.out.println(c);
        }
        System.out.println("========================================");
        pausar();
    }

    private static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
        }
    }

    private static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        limparTela();
    }

}
