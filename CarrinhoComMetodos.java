import java.util.InputMismatchException;
import java.util.Scanner;

public class CarrinhoComMetodos {
    static final double DESCONTO_CUPOM = 10.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int itenscarrinho = lerInteiroNoIntervalo(scanner, "Quantos produtos estão no carrinho? ", 1, 100);
        String[] nomes = new String[itenscarrinho];
        double[] precos = new double[itenscarrinho];
        int[] quantidades = new int[itenscarrinho];

        for (int i = 0; i < itenscarrinho; i++) {
            System.out.print("Produto " + (i + 1) + " - nome: ");
            nomes[i] = scanner.next();
            precos[i] = lerDoubleMaiorQueZero(scanner, "Preço unitário: ");
            quantidades[i] = lerInteiroNoIntervalo(scanner, "Quantidade: ", 1, 1000);
        }

        double total = calcularTotal(precos, quantidades);
        double percentual = calcularPercentualDesconto(total);
        double totalComDesconto = calcularTotal(precos, quantidades, percentual);
        boolean temCupom = perguntarSimNao(scanner, "Possui cupom de desconto? (s/n): ");
        double totalFinal = aplicarCupom(totalComDesconto, temCupom);
        int parcelas = lerInteiroNoIntervalo(scanner, "Em quantas parcelas (1 a 12): ", 1, 12);

        exibirNotaFinal(nomes, precos, quantidades, total, percentual, temCupom, totalFinal, parcelas);
        scanner.close();
    }

    static int lerInteiroNoIntervalo(Scanner scanner, String mensagem, int min, int max) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = scanner.nextInt();
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Digite um valor entre " + min + " e " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                scanner.next();
            }
        }
    }

    static double lerDoubleMaiorQueZero(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = scanner.nextDouble();
                if (valor > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que 0.");
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.next();
            }
        }
    }

    static boolean perguntarSimNao(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String resposta = scanner.next().trim().toLowerCase();
            if (resposta.equals("s")) {
                return true;
            }
            if (resposta.equals("n")) {
                return false;
            }
            System.out.println("Responda apenas com s ou n.");
        }
    }

    static double calcularSubtotal(double preco, int quantidade) {
        return preco * quantidade;
    }

    static double calcularTotal(double[] precos, int[] quantidades) {
        double total = 0.0;
        for (int i = 0; i < precos.length; i++) {
            total += calcularSubtotal(precos[i], quantidades[i]);
        }
        return total;
    }

    static double calcularTotal(double[] precos, int[] quantidades, double percentualDesconto) {
        double total = calcularTotal(precos, quantidades);
        return total - (total * percentualDesconto / 100);
    }

    static int indiceDoMaiorSubtotal(double[] precos, int[] quantidades) {
        int indice = 0;
        for (int i = 1; i < precos.length; i++) {
            if (calcularSubtotal(precos[i], quantidades[i]) > calcularSubtotal(precos[indice], quantidades[indice])) {
                indice = i;
            }
        }
        return indice;
    }

    static double calcularPercentualDesconto(double total) {
        if (total >= 500) {
            return 10;
        } else if (total >= 200) {
            return 5;
        }
        return 0;
    }

    static double aplicarCupom(double total, boolean temCupom) {
        if (!temCupom) {
            return total;
        }
        return Math.max(0, total - DESCONTO_CUPOM);
    }

    static double calcularParcela(double total, int parcelas) {
        return total / parcelas;
    }

    static int[] ordenarIndicesPorSubtotal(double[] precos, int[] quantidades) {
        int[] indices = new int[precos.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        for (int i = 0; i < indices.length - 1; i++) {
            for (int j = 0; j < indices.length - 1 - i; j++) {
                double atual = calcularSubtotal(precos[indices[j]], quantidades[indices[j]]);
                double proximo = calcularSubtotal(precos[indices[j + 1]], quantidades[indices[j + 1]]);
                if (atual < proximo) {
                    int temp = indices[j];
                    indices[j] = indices[j + 1];
                    indices[j + 1] = temp;
                }
            }
        }
        return indices;
    }

    static void exibirNotaFinal(String[] nomes, double[] precos, int[] quantidades, double total,
                                double percentual, boolean temCupom, double totalFinal, int parcelas) {
        int maior = indiceDoMaiorSubtotal(precos, quantidades);
        double totalComDesconto = calcularTotal(precos, quantidades, percentual);

        System.out.println("\n========== RESUMO DA COMPRA ==========");
        System.out.printf("Total sem desconto: R$ %.2f%n", total);
        System.out.printf("Desconto (%.0f%%): R$ %.2f%n", percentual, total - totalComDesconto);
        if (temCupom) {
            System.out.printf("Cupom: R$ %.2f%n", totalComDesconto - totalFinal);
        }
        System.out.printf("Total final: R$ %.2f%n", totalFinal);
        System.out.println("Produto com maior subtotal: " + nomes[maior]);
        System.out.printf("Maior subtotal: R$ %.2f%n", calcularSubtotal(precos[maior], quantidades[maior]));
        System.out.println("Quantidade de parcelas: " + parcelas);
        System.out.printf("Valor de cada parcela: R$ %.2f%n", calcularParcela(totalFinal, parcelas));

        System.out.println("\n----- Ranking por subtotal -----");
        int[] ranking = ordenarIndicesPorSubtotal(precos, quantidades);
        for (int i = 0; i < ranking.length; i++) {
            int p = ranking[i];
            System.out.printf("%d. %s - R$ %.2f%n", i + 1, nomes[p], calcularSubtotal(precos[p], quantidades[p]));
        }
        System.out.println("======================================");
    }
}
