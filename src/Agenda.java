import java.io.*;
import java.util.*;

public class Agenda {
    private List<Contato> contatos;
    private static String ARQUIVO_CONTATOS = "caixa.txt";

    private long idCounter;

    public Agenda() {
        contatos = new ArrayList<>();
        idCounter = 1;
        carregarContatos();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("##################");
            System.out.println("##### AGENDA #####");
            System.out.println("##################");
            System.out.println(">>>> Contatos <<<<");
            exibirContatos();
            System.out.println(">>>> Menu <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Remover Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");

            escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (escolha) {
                case 1:
                    adicionarContato();
                    break;
                case 2:
                    removerContato();
                    break;
                case 3:
                    editarContato();
                    break;
                case 4:
                    salvarContatos();
                    System.out.println("Saindo da agenda. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (escolha != 4);

    }

    private void exibirContatos() {
        System.out.println("Id | Nome ");
        for (Contato contato : contatos) {
            System.out.println(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome());
        }
    }

    private void adicionarContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o nome do contato: ");
        String nome = scanner.nextLine();

        // Verifica RN1
        if (contatos.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome))) {
            System.out.println("Já existe um contato com esse nome. Contato não adicionado.");
            return;
        }

        System.out.print("Informe o sobrenome do contato: ");
        String sobreNome = scanner.nextLine();

        List<Telefone> telefones = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            System.out.print("Informe o DDD do telefone " + (i + 1) + ": ");
            String ddd = scanner.nextLine();

            System.out.print("Informe o número do telefone " + (i + 1) + ": ");
            Long numero = scanner.nextLong();
            scanner.nextLine(); // Consumir a quebra de linha

            // Verifica RN2
            if (contatos.stream().anyMatch(c -> c.getTelefones().stream().anyMatch(t -> t.getNumero().equals(numero)))) {
                System.out.println("Já existe um contato com esse número de telefone. Contato não adicionado.");
                return;
            }

            telefones.add(new Telefone(ddd, numero));
        }

        Contato novoContato = new Contato(idCounter++, nome, sobreNome, telefones);
        contatos.add(novoContato);
        System.out.println("Contato adicionado com sucesso!");
    }

    private void removerContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o ID do contato a ser removido: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        // Verifica se o contato existe
        Optional<Contato> contatoParaRemover = contatos.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (contatoParaRemover.isPresent()) {
            contatos.remove(contatoParaRemover.get());
            System.out.println("Contato removido com sucesso!");
        } else {
            System.out.println("Contato não encontrado.");
        }
        exibirContatos();
        exibirMenu();
    }

    private void editarContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o ID do contato a ser editado: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir a quebra de linha

        // Verifica se o contato existe
        Optional<Contato> contatoParaEditar = contatos.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (contatoParaEditar.isPresent()) {
            Contato contato = contatoParaEditar.get();

            System.out.print("Informe o novo nome do contato: ");
            String novoNome = scanner.nextLine();

            // Verifica RN1
            if (!novoNome.equalsIgnoreCase(contato.getNome()) &&
                    contatos.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(novoNome))) {
                System.out.println("Já existe um contato com esse nome. Edição cancelada.");
                return;
            }

            System.out.print("Informe o novo sobrenome do contato: ");
            String novoSobreNome = scanner.nextLine();


            List<Telefone> telefones = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                System.out.print("Informe o novo DDD do telefone : ");
                String novoDDD = scanner.nextLine();

                System.out.print("Informe o novo número do telefone: ");
                Long novoNumero = scanner.nextLong();
                scanner.nextLine(); // Consumir a quebra de linha

                // Verifica RN2
                if (!novoNumero.equals(contato.getTelefones().get(i).getNumero()) &&
                        contatos.stream().anyMatch(c -> c.getTelefones().stream().anyMatch(t -> t.getNumero().equals(novoNumero)))) {
                    System.out.println("Já existe um contato com esse número de telefone. Edição cancelada.");
                    return;
                }


                telefones.add(new Telefone(novoDDD, novoNumero));

            }

            contato.setNome(novoNome);
            contato.setSobreNome(novoSobreNome);
            contato.setTelefones(telefones);

            System.out.println("Contato editado com sucesso!");
        } else {
            System.out.println("Contato não encontrado.");
        }

    }

    private void carregarContatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String line;
            long idCounter = 1; // Inicia o contador de IDs a partir de 1

            while ((line = reader.readLine()) != null) {
                String[] dados = line.split("-");

                // Verifica se há pelo menos 3 elementos no array
                if (dados.length >= 3) {
                    Long id = dados[0].equals("null") ? null : idCounter++; // Incrementa o ID
                    String nome = dados[1];
                    String sobreNome = dados[2];

                    List<Telefone> telefones = new ArrayList<>();
                    for (int i = 3; i < dados.length; i += 2) {
                        String ddd = dados[i];
                        Long numero = null;

                        // Verifica se há um número correspondente ao DDD
                        if (i + 1 < dados.length) {
                            numero = dados[i + 1].equals("null") ? null : Long.parseLong(dados[i + 1]);
                        }

                        telefones.add(new Telefone(ddd, numero));
                    }

                    contatos.add(new Contato(id, nome, sobreNome, telefones));
                } else {
                    System.out.println("Formato inválido na linha: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contatos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter número: " + e.getMessage());
        }
    }

    private void salvarContatos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTATOS))) {
            for (Contato contato : contatos) {
                writer.write(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome());

                for (Telefone telefone : contato.getTelefones()) {
                    writer.write("(" + telefone.getDdd() + ") " + telefone.getNumero());
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        agenda.exibirMenu();
    }
}

