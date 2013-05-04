// Arthur Henrique
// Filipe Kunioshi
// Francisco Nin
// Lucas Martino

package Buscas;

import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner entrada;
		Mapa mapa = new Mapa();
		boolean aberturaCompleta = false;
		
		try {
			// Recebe o caminho do arquivo "*.txt" do usu�rio
			String usuario;
			entrada = new Scanner(System.in);
			System.out.println("Digite o caminho do arquivo do arquivo de texto (*.txt):");
			usuario = entrada.next();
			
			// Abre o arquivo indicado
			entrada = new Scanner( new File(usuario) );
			
			// Verifica se o arquivo indic�do � do tipo "*.txt"
			if(usuario.lastIndexOf(".txt") != -1) {
				// Caso seja um TXT
				String auxString;
				int etapa = 0; // 0=Inicio | 1=Cidades | 2=Conex�es
				while(entrada.hasNextLine()) {
					auxString = entrada.nextLine();
					
					// Verifica a formata��o e a etapa da leitura
					if(auxString.equals("@cidades")) etapa = 1;
					else if(auxString.equals("@conexoes")) etapa = 2;
					else {
						// Monta o mapa (coloca as cidades no mapa)
						if(etapa == 1) {
							Scanner auxScan = new Scanner(auxString);
							auxScan.useDelimiter(" = ");
							
							// Vari�veis auxiliares para armazenar valores
							String auxNome;
							int auxX, auxY;
							
							// Recebe o nome da Cidade
							auxNome = auxScan.next();
							
							String auxScanCoord = auxScan.next();
							auxScan.close();
							auxScan = new Scanner(auxScanCoord);
							auxScan.useDelimiter("\\|");
							
							// Recebe as coordenadas
							auxX = Integer.parseInt(auxScan.next());
							auxY = Integer.parseInt(auxScan.next());
							
							// Monta uma lista simples das cidades
							mapa.addCidade(new Cidade(auxNome, auxX, auxY));
							
							auxScan.close();
						} else if(etapa == 2) {
							// Implementa as cidades gravadas
							Scanner auxScan = new Scanner(auxString);
							auxScan.useDelimiter("\\|");
							
							String cidade = auxScan.next();
							String conexao = auxScan.next();
							Cidade auxCidade;
							Cidade auxConexao;
							
							auxCidade = mapa.getCidade(cidade);
							auxConexao = mapa.getCidade(conexao);
							
							// Verifica se as cidades mencionadas existem, caso existam grava a liga��o em ambas as cidades (ida e volta)
							if(auxCidade == null || auxConexao == null) {
								System.out.println("Na conex�o " + cidade + "~" + conexao + " uma das cidades n�o foi encontrada.");
							} else {
								auxCidade.setProx(auxConexao);
								auxConexao.setProx(auxCidade);
							}
							
							auxScan.close();
						} else {
							// Caso continue sendo 0, significa que ocorreu algum erro na formata��o do arquivo
							break;
						}
					}
				}
				
				aberturaCompleta = true;
				
				// Mostra as cidades e suas liga��es
				for(Cidade c : mapa.getMapa()) {
					System.out.println(c.getNome());
					for(Cidade n : c.getProx()) {
						System.out.print("->");
						System.out.println(n.getNome());
					}
				}
				System.out.println("\n\n");
			} else {
				System.out.println("O arquivo indicado n�o � um '*.txt'");
			}
		} catch(FileNotFoundException e) {
			System.out.println("Erro na abertura do arquivo.");
		}
		
		if(aberturaCompleta){
			String inicio = "", fim = "";
			boolean loop = true;
			while(loop) {
				String usuario;
				entrada = new Scanner(System.in);
				
				// Recebe o tipo de busca que o usu�rio deseja realizar
				System.out.println("1 - Efetuar uma Busca em Largura");
				System.out.println("2 - Efetuar uma Busca em Profundidade");
				System.out.println("3 - Efetuar uma Busca A*");
				System.out.println("Digite o n�mero da opera��o que deseja realizar, ou digite qualquer outro caracter para fechar o programa: ");
				usuario = entrada.next();
				
				if(Integer.parseInt(usuario) > 0 && Integer.parseInt(usuario) <= 3) {
					// Vari�veis auxiliares
					int count = 0;
					
					// Mostra para o usu�rio as cidades dispin�veis e suas respectivas op��es
					while(count < mapa.getLength()) {
						System.out.println( (count + 1) + " - " + mapa.getNomeCidade(count) );
						count++;
					}
				}
				
				switch(Integer.parseInt(usuario)) {
					case 1:
						// Recebe a cidade que o usu�rio deseja partir (Ponto Inicial)
						System.out.println("Digite o n�mero da cidade de onde deseja partir (Ponto Inicial) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
						inicio = entrada.next();
						if(Integer.parseInt(inicio) <= mapa.getLength() && Integer.parseInt(inicio) > 0) {
							// Recebe a cidade que o usu�rio deseja chegar (Ponto Final)
							System.out.println("Digite o n�mero da cidade que deseja chegar (Ponto Final) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
							fim = entrada.next();
							if(Integer.parseInt(fim) <= mapa.getLength() && Integer.parseInt(fim) > 0) {
								// Caso todas as entradas sejam v�lidas, realiza a busca em largura
								Busca buscas = new Busca();
								buscas.buscaEmLargura( mapa.getCidade(Integer.parseInt(inicio) - 1), mapa.getCidade(Integer.parseInt(fim) - 1) );
							}

							System.out.println("Pressione ENTER para retornar ao menu inicial.");
							entrada.nextLine(); // Cambiarra para ele identificar um ENTER, com ou sem conte�do
							entrada.nextLine();
						}
						break;
					case 2:
						// Recebe a cidade que o usu�rio deseja partir (Ponto Inicial)
						System.out.println("Digite o n�mero da cidade de onde deseja partir (Ponto Inicial) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
						inicio = entrada.next();
						if(Integer.parseInt(inicio) <= mapa.getLength() && Integer.parseInt(inicio) > 0) {
							// Recebe a cidade que o usu�rio deseja chegar (Ponto Final)
							System.out.println("Digite o n�mero da cidade que deseja chegar (Ponto Final) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
							fim = entrada.next();
							if(Integer.parseInt(fim) <= mapa.getLength() && Integer.parseInt(fim) > 0) {
								// Caso todas as entradas sejam v�lidas, realiza a busca em largura
								Busca buscas = new Busca();
								buscas.buscaEmProfundidade( mapa.getCidade(Integer.parseInt(inicio) - 1), mapa.getCidade(Integer.parseInt(fim) - 1) );
							}
							
							System.out.println("Pressione ENTER para retornar ao menu inicial.");
							entrada.nextLine(); // Cambiarra para ele identificar um ENTER, com ou sem conte�do
							entrada.nextLine();
						}
						break;
					case 3:
						// Recebe a cidade que o usu�rio deseja partir (Ponto Inicial)
						System.out.println("Digite o n�mero da cidade de onde deseja partir (Ponto Inicial) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
						inicio = entrada.next();
						if(Integer.parseInt(inicio) <= mapa.getLength() && Integer.parseInt(inicio) > 0) {
							// Recebe a cidade que o usu�rio deseja chegar (Ponto Final)
							System.out.println("Digite o n�mero da cidade que deseja chegar (Ponto Final) ou digite qualquer outro caracter para voltar ao Menu Inicial: ");
							fim = entrada.next();
							if(Integer.parseInt(fim) <= mapa.getLength() && Integer.parseInt(fim) > 0) {
								// Caso todas as entradas sejam v�lidas, realiza a busca em largura
								Busca buscas = new Busca();
								buscas.buscaAStar( mapa.getNomeCidade(Integer.parseInt(inicio) - 1), mapa.getNomeCidade(Integer.parseInt(fim) - 1), mapa );
							}
							
							System.out.println("Pressione ENTER para retornar ao menu inicial.");
							entrada.nextLine(); // Cambiarra para ele identificar um ENTER, com ou sem conte�do
							entrada.nextLine();
						}
						
						break;
					default:
						loop = false;
						break;
				}
			}
		}
	}
}