package com.example.demo;

import java.util.ArrayList;
import java.lang.*;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import com.example.demo.entities.Proxies;
import com.example.demo.entities.ProxyLists;
import com.example.demo.repositories.ProxyListRepository;
import com.example.demo.repositories.ProxyRepository;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages= {"com.example.demo"})
public class ProxyListsApplication implements CommandLineRunner  {


	@Autowired
	private  ProxyRepository proxyRepository;
	@Autowired
	private  ProxyListRepository proxyListRepository;


	public static void main(String[] args)  {
		SpringApplication.run(ProxyListsApplication.class, args);

	}


	@Override

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		RestTemplate restTemplate= new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		String resourceURLs[]= {"https://gimmeproxy.com/api/getProxy","https://proxy11.com/api/proxy.json?key=MTI3OA.Xs--Lg.5FG2Pg_WoLej_ubo8rKpQQbaIZc", "https://api.getproxylist.com/proxy", "http://pubproxy.com/api/proxy"};


		System.out.println(resourceURLs.length);

		for(int p=0;p<resourceURLs.length;p++) {

			System.out.println(p +"<<<<<<<<<<<<<<<<<<< [[Data pulling has been started for all proxy List URL's]] >>>>>>>>>>>>>>"+ resourceURLs[p] );

			HttpEntity<String> entity=new HttpEntity<String>(headers);
			ResponseEntity<String> response=restTemplate.exchange(resourceURLs[p],HttpMethod.GET, entity, String.class);
			ProxyLists proxylists=new ProxyLists();
			proxylists.setUrl(resourceURLs[p]);       // saving URL in proxylist
			proxylists.setId(p); // saving id in proxylist
			//proxylists.getUrl();
			proxyListRepository.save(proxylists);

			System.out.println("Proxylist URL-> "+ resourceURLs[p]  +" has been saved in database ProxyLists");
			// Till here commom code.!

			if(resourceURLs[p].equals("https://proxy11.com/api/proxy.json?key=MTI3OA.Xs--Lg.5FG2Pg_WoLej_ubo8rKpQQbaIZc") ){
				System.out.println("<<<<<<<<<<<<<<<<<<<<<----------[[Proxy11 data is being pulled]]------------->>>>>>>>>>>>>>>>");


				JSONObject root=new JSONObject(response.getBody());
				JSONArray data= root.getJSONArray("data");

				for(int i=0;i<data.length();i++) {

					System.out.println("<<<<<<<<<<<<<<<<<<<< iteration "+ i +" has been started for proxy11.com api >>>>>>>>>>>>>>>>>>>>>>>");

					//boolean connectionStatus=false;
					//the json data


					JSONObject jsondata=data.getJSONObject(i);



					String ipaddress=jsondata.getString("ip");
					int port=jsondata.getInt("port");
					String an= String.valueOf( jsondata.getInt("type") );
					String anonymity;
					if(an.equals("0")) {

						anonymity="transparent";
					}else if(an.equals("1")) {
						anonymity="anonymous";
					} else {
						anonymity=an;
					}

					System.out.println("[[fetched data from proxy11.com]] "+ ipaddress+ " "+ " "+ port+ " "+ anonymity);

					Proxies proxy=new Proxies();
					proxy.setIpaddress(ipaddress);
					proxy.setPort(port);
					proxy.setId(p);
					proxy.setAnonymity(anonymity);
					System.out.println("setting data in object");
					String first_found_address= jsondata.getString("createdAt");
					String last_found_address= jsondata.getString("updatedAt");
					proxy.setIpaddress(ipaddress);
					proxy.setPort(port);
					proxy.setFirstFoundAddress(first_found_address);
					proxy.setLastFoundAddress(last_found_address);
					//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

					// commom code

					Proxy pr = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIpaddress(), proxy.getPort()));
					URL url;
					try {
						url = new URL("http://www.google.com");

						HttpURLConnection.setFollowRedirects(false);
						HttpURLConnection uc = (HttpURLConnection)url.openConnection(pr);
						System.out.println("---------------trying to connect--------------");
						uc.connect();
						uc.setConnectTimeout(3000);
						System.out.println("---------connected---------------");
						System.out.println("[[Trying to connect to fetched data]]");
						int responseCode= uc.getResponseCode();
						System.out.println(responseCode);



						try {
							if (responseCode==200){
								proxy.setTest_result("UP");
								System.out.println("[[ Successfully connect to fetched ipaddress and port from proxy11.com]]");
								System.out.println(response.getHeaders());
							}
							else {
								System.out.println("[[ unable connect to fetched ipaddress and port from proxy11.com]]");
								proxy.setTest_result("DOWN");
							}

						}                            
						catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.toString());
						}


						proxyRepository.save(proxy);
						System.out.println("successfully saved data into database");
					}
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  

					// common code
				}
			} 
			else if(resourceURLs[p].equals("http://pubproxy.com/api/proxy")) {
				System.out.println("<<<<<<<<<<<<<<<<<<<<<----------[[pubproxy data is being pulled]]------------->>>>>>>>>>>>>>>>");
				HttpEntity<String> entity1=new HttpEntity<String>(headers);
				ResponseEntity<String> response1=restTemplate.exchange(resourceURLs[p],HttpMethod.GET, entity1, String.class);
				System.out.println("<<<<<<<<<<<<<<<<<<<< iteration has been started for pubproxy api >>>>>>>>>>>>>>>>>>>>>>>");


				JSONObject root=new JSONObject(response1.getBody());
				JSONArray data= root.getJSONArray("data");
				JSONObject jsondata=data.getJSONObject(0);

				String ipaddress=jsondata.getString("ip");
				System.out.println("IP address of + pub proxy"+ ipaddress);
				int port=jsondata.getInt("port");
				System.out.println("Port of + pub proxy"+ port);

				String anonymity= jsondata.getString("proxy_level") ;
				System.out.println("anonymity of pub proxy"+ anonymity);

				System.out.println("[[fetched data from pubproxy]] "+ ipaddress+ " "+ " "+ port+ " "+ anonymity);


				Proxies proxy=new Proxies();
				proxy.setIpaddress(ipaddress);
				proxy.setPort(port);
				proxy.setId(p);
				proxy.setAnonymity(anonymity);


				//CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

				Proxy pr = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIpaddress(), proxy.getPort()));
				URL url;

				try {
					url = new URL("http://www.google.com");

					HttpURLConnection.setFollowRedirects(false);
					HttpURLConnection uc = (HttpURLConnection)url.openConnection(pr);
					System.setProperty("http.maxRedirects", "300");
					System.out.println("---------------trying to connect--------------");
					uc.connect();
					uc.setConnectTimeout(3000);
					System.out.println("---------connected---------------");
					int responseCode= uc.getResponseCode();
					System.out.println(responseCode);



					try {
						if (responseCode==200){
							proxy.setTest_result("UP");
							System.out.println("[[ Successfully connect to fetched ipaddress and port from pubproxy]]");
							System.out.println(response.getHeaders());


						}
						else {
							System.out.println("[[ unable connect to fetched ipaddress and port from pubproxy]]");
							proxy.setTest_result("DOWN");
						}

					}                            
					catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.toString());
					}


					proxyRepository.save(proxy);
					System.out.println("successfully saved data into database");

				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}







			} else if(resourceURLs[p].equals("https://api.getproxylist.com/proxy")) {

				System.out.println("<<<<<<<<<<<<<<<<<<<<<----------[[api.getproxylist data is being pulled]]------------->>>>>>>>>>>>>>>>");
				for(int i=0;i<=2;i++) {
					HttpEntity<String> entity2=new HttpEntity<String>(headers);
					ResponseEntity<String> response2=restTemplate.exchange(resourceURLs[p],HttpMethod.GET, entity2, String.class);
					System.out.println("<<<<<<<<<<<<<<<<<<<< iteration "+ i +" has been started for api.getproxylist >>>>>>>>>>>>>>>>>>>>>>>");

					JSONObject root=new JSONObject(response2.getBody());

					String ipaddress=root.getString("ip");
					System.out.println("ipaddress data: "+ ipaddress);
					int port=root.getInt("port");
					System.out.println("port data : "+ port);
					String anonymity= root.getString("anonymity") ;
					System.out.println("anonymity :"+ anonymity);

					System.out.println("[[fetched data from api.getproxylist]] "+ ipaddress+ " "+ " "+ port+ " "+ anonymity);



					Proxies proxy=new Proxies();
					proxy.setIpaddress(ipaddress);
					proxy.setPort(port);
					proxy.setId(p);
					proxy.setAnonymity(anonymity);

					System.out.println("setting data in object");

					Proxy pr = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIpaddress(), proxy.getPort()));
					URL url;
					try {
						url = new URL("http://www.google.com");

						HttpURLConnection.setFollowRedirects(false);
						HttpURLConnection uc = (HttpURLConnection)url.openConnection(pr);
						System.setProperty("http.maxRedirects", "300");
						System.out.println("---------------trying to connect--------------");
						uc.connect();
						uc.setConnectTimeout(3000);
						System.out.println("---------connected---------------");
						System.out.println("[[Trying to connect to fetched data]]");
						int responseCode= uc.getResponseCode();
						System.out.println("Response code is : "+responseCode);



						try {
							if (responseCode==200){
								proxy.setTest_result("UP");
								System.out.println("[[ Successfully connect to fetched ipaddress and port from api.getproxylist]]");
								System.out.println(response.getHeaders());
							}
							else {
								System.out.println("[[ unable connect to fetched ipaddress and port from api.getproxylist]]");
								proxy.setTest_result("DOWN");
							}

						}                            
						catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.toString());
						}


						proxyRepository.save(proxy);
						System.out.println("successfully saved data into database");
					}
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}



				}



			} else if(resourceURLs[p].equals("https://gimmeproxy.com/api/getProxy")) {

				System.out.println("\"<<<<<<<<<<<<<<<<<<<<<----------[[gimmeproxy data is being pulled]]------------->>>>>>>>>>>>>>>>\"");
				for(int i=0;i<=2;i++) {
					HttpEntity<String> entity3=new HttpEntity<String>(headers);
					ResponseEntity<String> response3=restTemplate.exchange(resourceURLs[p],HttpMethod.GET, entity3, String.class);
					System.out.println("<<<<<<<<<<<<<<<<<<<< iteration "+ i +" has been started for gimmeproxy >>>>>>>>>>>>>>>>>>>>>>>");

					JSONObject root=new JSONObject(response3.getBody());

					String ipaddress=root.getString("ip");
					int port=root.getInt("port");
					String an= String.valueOf( root.getInt("anonymityLevel") );
					String anonymity;
					if(an.equals("1")) {

						anonymity="elite";
					}else if(an.equals("2")) {
						anonymity="anonymous";
					} else {
						anonymity="transparent";
					}

					System.out.println("[[fetched data from gimmeproxy]] "+ ipaddress+ " "+ " "+ port+ " "+ anonymity);


					Proxies proxy=new Proxies();
					proxy.setIpaddress(ipaddress);
					proxy.setPort(port);
					proxy.setId(p);
					proxy.setAnonymity(anonymity);
					System.out.println("setting data in object");

					Proxy pr = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.getIpaddress(), proxy.getPort()));
					URL url;

					try {
						url = new URL("http://www.google.com");

						HttpURLConnection.setFollowRedirects(false);
						HttpURLConnection uc = (HttpURLConnection)url.openConnection(pr);
						System.out.println("---------------trying to connect--------------");
						System.setProperty("http.maxRedirects", "300");
						uc.connect();
						uc.setConnectTimeout(3000);
						System.out.println("---------connected---------------");
						int responseCode= uc.getResponseCode();
						System.out.println(responseCode);



						try {
							if (responseCode==200){
								proxy.setTest_result("UP");
								System.out.println("[[ Successfully connect to fetched ipaddress and port from gimmeproxy.com]]");
								System.out.println(response.getHeaders());

							}
							else {
								System.out.println("[[ unable connect to fetched ipaddress and port from gimmeproxy.com]]");
								proxy.setTest_result("DOWN");
							}

						}                            
						catch (Exception e) {
							e.printStackTrace();
							System.out.println(e.toString());
						}


						proxyRepository.save(proxy);
						System.out.println("successfully saved data into database");
					}
					catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}

			else {System.out.println("<<<<<<<<<<<<<<<<<<<<<<<-----------Equals condition failed for--------------->>>>>>>>>>>> "+ resourceURLs[p]);}
		}

	}


	@Autowired
	private  ProxyRepository proxyRepository1;
	@Autowired
	private ProxyListRepository proxyListRepository1;

	@Scheduled(cron = "0 */59 * ? * *")
	public  void publish() throws Exception {
		List<Proxies> list=proxyRepository1.findAll();
		proxyRepository1.deleteAll(list);
		List<ProxyLists> proxylist=proxyListRepository1.findAll();
		proxyListRepository1.deleteAll(proxylist);
		run();


	}
}