package Rpg_Unipar.iml;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void mostrarInicio() {
        System.err.println("***********");
        System.err.println("  JOGO RPG ");
        System.err.println("***********");
    }

    public static int escolhaPersonagem() {
        Scanner sc = new Scanner(System.in);
        int opcaoClasse = 0;

        do {
            System.out.println("Escolha sua classe :");
            System.out.println("1- Guerreiro");
            System.out.println("2- Arqueiro");
            System.out.println("3- Shaman");
            System.out.println("4- Mago");
            opcaoClasse = sc.nextInt();
        } while (opcaoClasse <= 0 || opcaoClasse >= 5);

        return opcaoClasse;
    }

    public static int sortearDado20() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static int explorar() {
        Random random = new Random();
        int sorteio = random.nextInt(100) + 1;

        if (sorteio <= 40) {
            return 1; // Inimigo
        } else if (sorteio <= 55) {
            return 2; // Energia
        } else if (sorteio <= 80) {
            return 3; // Vidinha
        } else if (sorteio <= 90) {
            return 4; // Item Raro
        } else {
            return 5; // Poção
        }
    }

    public static int combater(int vida, int energia, int ataque, int defesa, boolean temArma, boolean temArmadura) {
        Random random = new Random();

        int monstroEspecial = random.nextInt(20) + 1;
        int vidaMonstro = random.nextInt(80) + 40 + monstroEspecial;
        int ataqueMonstro = random.nextInt(30) + 5;

        if (vidaMonstro >= 100) {
            System.out.println("Voce encontrou o Marcelo Goblin");
        }

        if (temArmadura) {
            defesa += 20;
        }
        if (temArma) {
            ataque += 20;
        }

        System.out.println("Seu inimigo tem: ");
        System.out.println("Vida: " + vidaMonstro);
        System.out.println("Ataque: " + ataqueMonstro);

        while (vidaMonstro > 0 && vida > 0) {
            int sorteioDado = sortearDado20();

            if (sorteioDado <= 7) {
                System.out.println("Você errou!");
            } else if (sorteioDado <= 14) {
                vidaMonstro -= ataque / 2;
                System.out.println("Metade do dano");
            } else if (sorteioDado <= 19) {
                vidaMonstro -= ataque;
                System.out.println("Ataque total");
            } else {
                vidaMonstro -= ataque * 2;
                System.out.println("ATAQUE CRITICO!");
            }

            if (vidaMonstro > 0) {
                sorteioDado = sortearDado20();

                if (sorteioDado <= 7) {
                    System.out.println("O Goblin errou!");
                } else if (sorteioDado <= 14) {
                    vida -= (ataqueMonstro - (defesa / 4)) / 2;
                    System.out.println("Metade do dano (Defesa amorteceu)");
                } else if (sorteioDado <= 19) {
                    vida -= (ataqueMonstro - (defesa / 4));
                    System.out.println("Ataque Total (Defesa amorteceu)");
                } else {
                    vida -= ataqueMonstro * 2;
                    System.out.println("Você recebeu um critico!");
                }

                System.err.println("Vida Personagem: " + vida);
                System.err.println("Vida Monstro: " + vidaMonstro);
            }
        }

        if (vidaMonstro > 0) {
            System.out.println("o Goblin te pegou!");
            return 0;
        } else {
            System.out.println("Você Matou ele!");
            return vida;
        }
    }

    public static void mostrarStatus(String nome, int ataque, int defesa, int vida, int vidaMaxima, int energia, int nivel, int xp, int pocoes, int totalExploracoes, int monstrosMortos, boolean temArmadura, boolean temArma) {
        System.out.println("\n--- STATUS DE " + nome.toUpperCase() + " ---");
        System.out.println("Nível: " + nivel + " | XP: " + xp + "/100");
        System.out.println("Vida: " + vida + "/" + vidaMaxima);
        System.out.println("Ataque: " + ataque);
        System.out.println("Defesa: " + defesa);
        System.out.println("Energia: " + energia);
        System.out.println("Poções de Cura: " + pocoes);
        System.out.println("Total de Explorações: " + totalExploracoes);
        System.out.println("Monstros Derrotados: " + monstrosMortos);
        System.out.println("Armadura: " + (temArmadura ? "Sim" : "Não"));
        System.out.println("Arma: " + (temArma ? "Sim" : "Não"));
        System.out.println("-----------------------------");
    }

    public static void main(String[] args) throws InterruptedException {
        mostrarInicio();

        Scanner sc = new Scanner(System.in);

        System.out.println("Informe o nome do seu personagem:");
        String nome = sc.next();
        int vida = 100;
        int ataque = 0;
        int defesa = 0;
        int energia = 0;

        int nivel = 1;
        int xp = 0;
        int pocoes = 1;

        // Variáveis de estatísticas de exploração e combate
        int totalExploracoes = 0;
        int monstrosMortos = 0;

        boolean temArma = false;
        boolean temArmadura = false;

        System.out.println("Seja bem-vindo " + nome + " ! ");

        int opcao = escolhaPersonagem();

        if (opcao == 1) {
            vida = 120;
            ataque = 80;
            defesa = 50;
        } else if (opcao == 2) {
            vida = 100;
            ataque = 70;
            defesa = 40;
        } else if (opcao == 3) {
            vida = 80;
            ataque = 50;
            defesa = 60;
        } else {
            vida = 90;
            ataque = 40;
            defesa = 20;
        }

        int vidaMaxima = vida;
        int opcaoJogo = -1;

        while (opcaoJogo != 0) {
            System.out.println("\n--- Inicio da jornada ---");
            System.out.println("1- Explorar");
            System.out.println("2- Ver status");
            System.out.println("3- Descansar (Recuperar Vida/Energia)");
            System.out.println("4- Usar Poção de Vida (" + pocoes + " disponíveis)");
            System.out.println("0- Sair");

            opcaoJogo = sc.nextInt();

            if (opcaoJogo == 1) {
                totalExploracoes++; // Conta mais uma exploração realizada
                int opcaoExplorar = explorar();

                if (opcaoExplorar == 1) {
                    System.out.println("Voce Encontrou um Goblin");
                    System.out.println("Voce quer fazer o que?");
                    System.out.println("1 - Atacar");
                    System.out.println("2 - Defender");
                    System.out.println("3 - Fugir");

                    int opcaoLuta = sc.nextInt();

                    if (opcaoLuta == 1) {
                        vida = combater(vida, energia, ataque, defesa, temArma, temArmadura);

                        if (vida <= 0) {
                            System.err.println("VOCÊ MORREUU!");
                            break;
                        } else {
                            monstrosMortos++; // Incrementa o monstro derrotado

                            int xpGanho = 40;
                            xp += xpGanho;
                            System.out.println("Você ganhou +" + xpGanho + " de XP!");

                            if (xp >= 100) {
                                nivel++;
                                xp -= 100;
                                vidaMaxima += 20;
                                vida = vidaMaxima;
                                ataque += 15;
                                defesa += 10;
                                System.out.println("\n ★★★ LEVEL UP! Você subiu para o Nível " + nivel + "! ★★★");
                            }
                        }
                    } else if (opcaoLuta == 2) {
                        System.out.println("Você assumiu postura defensiva e escapou ileso!");
                    } else if (opcaoLuta == 3) {
                        System.out.println("Você fugiu correndo!");
                    } else {
                        System.out.println("Opcao invalida");
                    }

                } else if (opcaoExplorar == 2) {
                    System.out.println("Voce Encontrou um bau de energia");

                    if (energia + 1 < 10) {
                        energia += 1;
                        System.out.println("Energia aumentada em +1!");
                    } else {
                        System.out.println("Energia cheia!");
                    }

                } else if (opcaoExplorar == 3) {
                    System.out.println("Voce seguiu sua vidinha em paz...");

                } else if (opcaoExplorar == 4) {
                    System.out.println("Sortudinho, ganhou item raro!");

                    Random random = new Random();
                    int itemSorteado = random.nextInt(2) + 1;

                    if (itemSorteado == 1) {
                        if (temArmadura) {
                            System.out.println("Voce ja tem armadura equipada!");
                        } else {
                            temArmadura = true;
                            System.out.println("Armadura equipada (+20 Defesa)!");
                        }
                    } else {
                        if (temArma) {
                            System.out.println("Voce ja esta armado!");
                        } else {
                            temArma = true;
                            System.out.println("Arma equipada (+20 Ataque)!");
                        }
                    }
                } else if (opcaoExplorar == 5) {
                    System.out.println("Você encontrou uma Poção de Cura no chão!");
                    pocoes++;
                    System.out.println("Total de poções no inventário: " + pocoes);
                }

            } else if (opcaoJogo == 2) {
                mostrarStatus(nome, ataque, defesa, vida, vidaMaxima, energia, nivel, xp, pocoes, totalExploracoes, monstrosMortos, temArmadura, temArma);

            } else if (opcaoJogo == 3) {
                vida += 15;

                if (vida >= vidaMaxima) {
                    vida = vidaMaxima;
                    System.out.println("Vida máxima atingida!");
                } else {
                    System.out.println("Vida aumentada!");
                }

                System.out.println("Recuperando energia...");
                Thread.sleep(1000);
                System.out.println("Energia recuperada!");
                energia += 1;

            } else if (opcaoJogo == 4) {
                if (pocoes > 0) {
                    if (vida == vidaMaxima) {
                        System.out.println("Sua vida já está cheia!");
                    } else {
                        pocoes--;
                        vida += 40;
                        if (vida > vidaMaxima) {
                            vida = vidaMaxima;
                        }
                        System.out.println("Você usou uma poção! Vida atual: " + vida + "/" + vidaMaxima);
                    }
                } else {
                    System.out.println("Você não tem poções de cura!");
                }

            } else if (opcaoJogo == 0) {
                System.out.println("Saindo do jogo... Até logo, " + nome + "!");
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }
}