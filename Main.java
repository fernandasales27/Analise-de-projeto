package br.com.nomaroma.presentation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import br.com.nomaroma.entities.Account;
import br.com.nomaroma.entities.AccountEnum;
import br.com.nomaroma.entities.Client;
import br.com.nomaroma.service.BankService;
import br.com.nomaroma.service.ServiceFactory;

/**
 * OBSERVAçõES: 
 * NãO é permitido o uso de nenhuma estrutura de repetição (for, while, do-while).
 * Tente, ao máximo, evitar o uso das estruturas if, else if, else e switch-case
 * 
 * @author VictorLira
 *
 */
public class Main {
	
	private static BankService service = ServiceFactory.getService();
	
	public static void main(String[] args) {
		//1:
		/*imprimirNomesClientes();*/
		
		//2:
		/*imprimirMediaSaldos();*/
		
		//3:
		//imprimirPaisClienteMaisRico();
		
		//4:
		/*imprimirSaldoMedio(6);*/
		
		//5:
		imprimirClientesComPoupanca();
		
		//6:
        /*System.out.println(getEstadoClientes(25));*/
		
		//7:
       /*System.out.println(getNumerosContas("Brazil"));*/
		
		//8:
	   	/*System.out.println(getMaiorSaldo("client39@bank.com"));*/
		
		//9:
		/*sacar(190,570,200);*/
		
		//10:
		/*depositar("Brazil",200);*/
		
		//11:
		/*transferir(198, 576, 594, 200);*/
		
		//13:
		/*System.out.println(getSomaContasEstado("State 6"));*/
		
		//14:
		/*System.out.println(getEmailsClientesContasConjuntas());*/
		
		//15:
		/*System.out.println(isPrimo(3));*/
		
		//16:
		/*System.out.println(getFatorial(7));*/
		
	}
	
	/**
	 * 1. Imprima na tela o nome e e-mail de todos os clientes (sem repeti��o), usando o seguinte formato:
	 * Victor Lira - vl@cin.ufpe.br
	 */
	public static void imprimirNomesClientes() {
		service
			.listClients()
			.stream()
			.distinct()
			.forEach(c-> System.out.println(c.getName()  +" - "+ c.getEmail()));
	}
	
	/**
	 * 2. Imprima na tela o nome do cliente e a m�dia do saldo de suas contas, ex:
	 * Victor Lira - 352
	 */
	public static void imprimirMediaSaldos() {
		
		service
		.listClients()
		.stream()
		.forEach(c-> System.out.println(c.getName()  +" - "+ c.getAccounts().stream().mapToDouble(a->a.getBalance()).average().orElse(0)));

	}
	
	/**
	 * 3. Considerando que s� existem os pa�ses "Brazil" e "United States", 
	 * imprima na tela qual deles possui o cliente mais rico, ou seja,
	 * com o maior saldo somando todas as suas contas.
	 */
	public static void imprimirPaisClienteMaisRico() {
		service
		.listClients()
		.stream()
		.peek((c->c.getAccounts().stream().mapToDouble(a->a.getBalance()).sum();
		.peek(c->c.getAdress().stream().getCountry));
	
	//.forEach(c-> System.out.println(c.getName()  +" - "+ c.getAddress().stream().mapToString(a->a.getCountry()).stream().mapToDouble(a->a.getBalance()).max()));

	}
	
	/**
	 * 4. Imprime na tela o saldo m�dio das contas da ag�ncia
	 * @param agency
	 */
	public static void imprimirSaldoMedio(int agency) {
		service
		.listAccounts()
		.stream()
		.filter(c->c.getAgency()==agency)
		//.forEach(c-> System.out.println(c.getBalance()).average().orElse(0.0));
		.mapToDouble(c->c.getBalance()).average().orElse(0.0);
		
		//.filter(c-> c.getAgency().stream().average())
		//.map(c->c.getAccounts().g)
		//.forEach(c-> System.out.println(c.getAccounts().stream().mapToDouble(a->a.getAgency().getBalace())));
		
		
		
	}
	/**
	 * 5. Imprime na tela o nome de todos os clientes que possuem conta poupan�a (tipo SAVING)
	 */
	public static void imprimirClientesComPoupanca() {
		service
		.listAccounts()
		.stream()
		.filter(a->AccountEnum.SAVING.equals(a.getType()))
		.forEach(a->System.out.println(a.getClient().getName()));	
	
		
	}
	
	/**
	 * 6.
	 * @param agency
	 * @return Retorna uma lista de Strings com o "estado" de todos os clientes da ag�ncia
	 */
	public static List<String> getEstadoClientes(int agency) {
		//throw new UnsupportedOperationException();
		return
		service
		.listAccounts()
		.stream()
		.filter(a->a.getAgency()==agency)
		.map(a-> a.getClient().getAddress().getState())
		.collect(Collectors.toList());
		
	}
	
	/**
	 * 7.
	 * @param country
	 * @return Retorna uma lista de inteiros com os n�meros das contas daquele pa�s
	 */
	public static List<Integer> getNumerosContas(String country) {
		//throw new UnsupportedOperationException();
		return
		service
		.listClients()
		.stream()
		.filter(c->c.getAddress().getCountry().equals(country))
		.flatMap(c->c.getAccounts().stream().map(a->a.getNumber()))
		.collect(Collectors.toList());
		
	}
	
	/**
	 * 8.
	 * Retorna o somat�rio dos saldos das contas do cliente em quest�o 
	 * @param clientEmail
	 * @return
	 */
	public static double getMaiorSaldo(String clientEmail) {
		//throw new UnsupportedOperationException();
		return
		service
		.listAccounts()
		.stream()
		.filter(c->c.getClient().getEmail().equals(clientEmail))
		.mapToDouble(c->c.getBalance()).sum();
		
	}
	
	/**
	 * 9.
	 * Realiza uma opera��o de saque na conta de acordo com os par�metros recebidos
	 * @param agency
	 * @param number
	 * @param value
	 */
	public static void sacar(int agency, int number, double value) {
		service
		.listAccounts()
		.stream()
		.filter(c->c.getAgency()==agency && c.getNumber() == number).findFirst()
		.ifPresent(c->{ 
			if(value<=c.getBalance()) {
				c.setBalance(c.getBalance()-value);
				System.out.println("saque realizado com com sucesso!");
			}
			else {
				System.out.println("saldo insuficiente");
			}
		});
	}
	/**
	 * 10. Realiza um deposito para todos os clientes do pa�s em quest�o	
	 * @param country
	 * @param value
	 */
	public static void depositar(String country, double value) {
		service
		.listClients()
		.stream()
		.filter(c->c.getAddress().getCountry().equals(country))
		.forEach(c->c.getAccounts().forEach(a->a.setBalance(a.getBalance()+value)));
		System.out.println("deposito realizado para os clientes de "+ country);
		
	}
	
	/**
	 * 11. Realiza uma transfer�ncia entre duas contas de uma ag�ncia.
	 * @param agency - ag�ncia das duas contas
	 * @param numberSource - conta a ser debitado o dinheiro
	 * @param numberTarget - conta a ser creditado o dinheiro
	 * @param value - valor da transfer�ncia
	 */
	public static void transferir(int agency, int numberSource, int numberTarget, double value) {
		service
		.listAccounts()
		.stream()
		.filter(a->a.getAgency()== agency && a.getNumber()==numberSource && a.getNumber()== numberTarget)
		.forEach(a->a.this.numberSource.);
	}
	
	/**
	 * 12.
	 * @param clients
	 * @return Retorna uma lista com todas as contas conjuntas (JOINT) dos clientes
	 */
	public static List<Account> getContasConjuntas(List<Client> clients) {
	//	throw new UnsupportedOperationException();
		return clients
		.stream()
		.flatMap(c-> c.getAccounts().stream())
		.filter(c-> AccountEnum.JOINT.equals(c.getType()))
		.collect(Collectors.toList());
	}
	
	/**
	 * 13.
	 * @param state
	 * @return Retorna uma lista com o somat�rio dos saldos de todas as contas do estado 
	 */
	public static double getSomaContasEstado(String state) {
		//throw new UnsupportedOperationException();
		return
		service
		.listAccounts()
		.stream()
		.filter(a->a.getClient().getAddress().getState().equals(state))
		.mapToDouble(a->a.getBalance()).sum();
			
	}
	
	/**
	 * 14.
	 * @return Retorna um array com os e-mails de todos os clientes que possuem contas conjuntas
	 */
	public static String[] getEmailsClientesContasConjuntas() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 15.
	 * @param number
	 * @return Retorna se o n�mero � primo ou n�o
	 */
	public static boolean isPrimo(int number) {
		//throw new UnsupportedOperationException();
	}
	
	
	/**
	 * 16.
	 * @param number
	 * @return Retorna o fatorial do n�mero
	 */
	public static int getFatorial(int number) {
		throw new UnsupportedOperationException();
	}
}
