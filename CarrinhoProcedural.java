
import java.util.Scanner;
public class CarrinhoProcedural {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        double total = 0.0;
        double totaldesconto = 0.0;
        double MaiorSubtotal = 0.0;
        String nomeProdutoMaior = "";
        int itenscarrinho = 0;
        int quantidade = 0;
        double preco = 0;

        do {
            System.out.println("quantos produtos estão no carrinho?");
            itenscarrinho = scanner.nextInt();
            if (itenscarrinho <= 0){
                System.out.println("A quantidade precisa ser maior que zero");
            }
        } while (itenscarrinho <= 0);

        for (int i = 1; i <= itenscarrinho; i++){
            System.out.print("Produto " + i + " - nome: ");
            String nomeProduto = scanner.next();

            do {
                System.out.print("Preço unitário: ");
                preco = scanner.nextDouble();
                if (preco <= 0){
                    System.out.println("O preço deve ser maior que 0");
                }
            } while (preco <= 0);

            do {
                System.out.print("Quantidade: ");
                quantidade = scanner.nextInt();
                if (quantidade <= 0){
                    System.out.println("A quantidade deve ser maior que 0");
                }
            } while (quantidade <= 0);

            double subtotal = (quantidade * preco);
            total = total + subtotal;

            if (subtotal > MaiorSubtotal){
                nomeProdutoMaior = nomeProduto;
                MaiorSubtotal = subtotal;
            }
        }

        if (total >= 500){
            totaldesconto = total - (total * 0.1);
        }
        else if (total >= 200){
            totaldesconto = total - (total * 0.05);
        }
        else{
            totaldesconto = total;
        }

        System.out.print("Em quantas parcelas (1 a 12): ");
        int parcelas = scanner.nextInt();

        while (parcelas < 1 || parcelas > 12){
            System.out.println("Escolha uma quantidade entre 1 e 12 parcelas.");
            System.out.print("Em quantas parcelas (1 a 12): ");
            parcelas = scanner.nextInt();
        }

        double desconto = total - totaldesconto;
        double valorparcela = totaldesconto / parcelas;

        System.out.println("\n========== RESUMO DA COMPRA ==========");
        System.out.printf("Total sem desconto: R$ %.2f%n", total);
        System.out.printf("Desconto aplicado: R$ %.2f%n", desconto);
        System.out.printf("Total com desconto: R$ %.2f%n", totaldesconto);
        System.out.println("Produto com maior subtotal: " + nomeProdutoMaior);
        System.out.printf("Maior subtotal: R$ %.2f%n", MaiorSubtotal);
        System.out.println("Quantidade de parcelas: " + parcelas);
        System.out.printf("Valor de cada parcela: R$ %.2f%n", valorparcela);
        System.out.println("======================================");

        scanner.close();
    }
}
