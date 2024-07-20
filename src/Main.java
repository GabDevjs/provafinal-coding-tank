import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int MAX_FUNCIONARIOS = 5;
        Scanner input = new Scanner(System.in);

        double[] salariosBrutos;
        double[] salariosComDesconto;

        double[] descontoINSS;
        double[] descontoImpostoRenda;
        double[] valorsTotaisDoDesconto;

        System.out.println("########## Calculo de salario ########## %n");

        salariosBrutos = new double[MAX_FUNCIONARIOS];
        salariosBrutos = setSalariosBrutos(input, MAX_FUNCIONARIOS);

        System.out.println("%n ############### %n");

        descontoINSS = new double[MAX_FUNCIONARIOS];
        descontoImpostoRenda = new double[MAX_FUNCIONARIOS];
        valorsTotaisDoDesconto = new double[MAX_FUNCIONARIOS];

        salariosComDesconto = setSalariosComDescontoAndValoresDeDesconto(input, salariosBrutos, MAX_FUNCIONARIOS, valorsTotaisDoDesconto, descontoINSS, descontoImpostoRenda);

        logValoresFinaisFormtado(MAX_FUNCIONARIOS, salariosBrutos, salariosComDesconto, valorsTotaisDoDesconto, descontoINSS, descontoImpostoRenda);

        input.close();
    }

    private static double[] setSalariosBrutos(Scanner input, int MAX_FUNCIONARIOS) {
        double entrada;
        boolean valorValido = false;
        double[] novosSalariosBrutos = new double[MAX_FUNCIONARIOS];

        for (int i = 0; i < MAX_FUNCIONARIOS; i++) {
            System.out.println("Digite o " + getPosicaoSalarioLabel(i) + " Salario :");

            while (!valorValido) {
                try {
                    entrada = Double.parseDouble(input.nextLine());
                    novosSalariosBrutos[i] = entrada;
                    valorValido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Valor Digitado Não é valido, tente novamente com um valor valido");
                    System.out.println("Info: para o valor ser valido ele tem que ser um numero. ");
                }
            }

            valorValido = false;
        }

        return novosSalariosBrutos;
    }

    private static String getPosicaoSalarioLabel(int index) {
        return switch (index + 1) {
            case 1 -> "Primeiro";
            case 2 -> "Segundo";
            case 3 -> "Terceiro";
            case 4 -> "Quarto";
            case 5 -> "Quinto";
            default -> "";
        };
    }


    private static double[] setSalariosComDescontoAndValoresDeDesconto(Scanner input, double[] salariosBrutos, int MAX_FUNCIONARIOS, double[] valorsTotaisDoDesconto, double[] descontoINSS, double[] descontoImpostoRenda) {
        double[] novosSalariosComDesconto;
        novosSalariosComDesconto = new double[MAX_FUNCIONARIOS];

        for (int i = 0; i < MAX_FUNCIONARIOS; i++) {
            descontoINSS[i] = getDescontoINSSValor(salariosBrutos[i]);
            descontoImpostoRenda[i] = getDescontoImpostoRenda(salariosBrutos[i]);

            valorsTotaisDoDesconto[i] = descontoINSS[i] + descontoImpostoRenda[i];
            novosSalariosComDesconto[i] = salariosBrutos[i] - valorsTotaisDoDesconto[i];
        }

        return novosSalariosComDesconto;
    }

    private static double getDescontoImpostoRenda(double salario) {
        double valorDesconto;

        if (salario >= 0 && salario <= 1903.98) {
            valorDesconto = calculePorcentagem(0, salario);
        } else if (salario >= 1903.99 && salario <= 2826.65) {
            valorDesconto = calculePorcentagem(7.5, salario);
        } else if (salario >= 2826.66 && salario <= 3751.05) {
            valorDesconto = calculePorcentagem(15, salario);
        } else if (salario >= 3751.06 && salario <= 4664.68) {
            valorDesconto = calculePorcentagem(22.5, salario);
        } else if (salario >= 4664.69) {
            valorDesconto = calculePorcentagem(27.5, salario);
        } else {
            valorDesconto = 0;
        }

        return valorDesconto;
    }

    private static double getDescontoINSSValor(double salario) {
        double valorDesconto;

        if (salario >= 0 && salario <= 1212.00) {
            valorDesconto = calculePorcentagem(7.5, salario);
        } else if (salario >= 1212.01 && salario <= 2427.35) {
            valorDesconto = calculePorcentagem(9, salario);
        } else if (salario >= 2527.36 && salario <= 3641.03) {
            valorDesconto = calculePorcentagem(12, salario);
        } else if (salario >= 3641.04 && salario <= 7087.22) {
            valorDesconto = calculePorcentagem(14, salario);
        } else if (salario >= 7087.23) {
            valorDesconto = calculePorcentagem(14, salario);
        } else {
            valorDesconto = 0;
        }

        return valorDesconto;
    }

    private static double calculePorcentagem(double porcentagem, double salario) {
        return ((porcentagem * salario) / 100);
    }

    private static void logValoresFinaisFormtado(int MAX_FUNCIONARIOS, double[] salariosBrutos, double[] salariosComDesconto, double[] valorsTotaisDoDesconto, double[] descontoINSS, double[] descontoImpostoRenda) {
        for (int i = 0; i < MAX_FUNCIONARIOS; i++) {
            System.out.println("--------------------------");
            System.out.println("Log do " + getPosicaoSalarioLabel(i) + " Salario Liquido mais a descricao dos descontos: ");
            System.out.println("Salario Liquido: " + salariosComDesconto[i]);
            System.out.println("Salario Bruto: " + salariosBrutos[i]);
            System.out.println("###########");
            System.out.println("-- Descontos -- ");
            System.out.println("Desconto do INSS: " + descontoINSS[i]);
            System.out.println("Desconto do Imposto de renda: " + descontoImpostoRenda[i]);
            System.out.println("Descontos Totais: " + valorsTotaisDoDesconto[i]);
        }

        return;
    }

}