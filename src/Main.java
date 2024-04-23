import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Scanner leitura = new Scanner(System.in);
        String busca = "";

        List<Enderecorec> end = new ArrayList<>();


        while (!busca.equalsIgnoreCase("sair")) {
            System.out.println("Digite um cep:");
            busca = leitura.next();

            if(busca.equalsIgnoreCase("sair")){
                break;
            }

//        URI endereco = URI.create("https://viacep.com.br/ws/"+"28615230"+"/json/");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
//                .uri(endereco)
                    .uri(URI.create("https://viacep.com.br/ws/"+busca+"/json/"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            Enderecorec newEndereco = gson.fromJson(response.body(), Enderecorec.class);
            end.add(newEndereco);
            System.out.println(end);

//            FileWriter escrita = new FileWriter("buscados.txt");
//            escrita.write(end.toString());
//            escrita.close();

            FileWriter escrita = new FileWriter("minhasConsultas"+".json");
            escrita.write(gson.toJson(end));
            escrita.close();
        }
    }
}
