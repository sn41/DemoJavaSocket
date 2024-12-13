import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//TIP пример "https://java-course.ru/begin/networking/"
// Программа - клиент, которая создает сокет, и подключается к существующему сокету google.com.
// По умолчанию номер порта для приема запросов от браузеров равен “80”
// Сайт google.com имеет совершенно конкретный IP-адрес, который может быть найден с помощью системы DNS — Domain Name System (система доменных имен).
// Будем считать, что для того, чтобы получить определенный текст с указанного сайта, нам надо послать определенную строку.
public class Main {
    public static void main(String[] args) throws IOException {

        /*
         * Конструктор получает имя хоста и номер порта, с которым хотите соединиться.
         * Socket сам ищет нужный IP по DNS,
         * самостоятельно получает порт на локальном компьютере
         * и делает соединение с указанным хостом.
         * Сокет предоставляет два потока: чтение — InputStream, запись — OutputStream.
         * */

        // Открываем сокет для доступа к компьютеру
        // по адресу "java-course.ru" (порт 80)
        Socket mySocket = new Socket("java-course.ru", 80);

        // Поток для чтения
        InputStream in = mySocket.getInputStream();
        // Поток для записи
        OutputStream out = mySocket.getOutputStream();

        // Строка запроса к серверу
        String requestGet = "GET /network.txt HTTP/1.1\r\n" +
                "Host:java-course.ru\r\n\r\n";

        // Преобразуем строку в массив байт
        byte requestInBytes[] = requestGet.getBytes();
        // Пишем в поток вывода
        out.write(requestInBytes);


        //Подготовим буфер для чтения ответа
        byte buf_out[] = new byte[1024];

        // Организуем цикл чтения ответа сервера
        // Читаем поток данных, получаемый сокетом с сайта
        do {
            int size = in.read(buf_out);
            //если ничего из потока не прочиталось, выходим из цикла
            if (size == -1) {
                break;
            } else {
                //иначе - выводим прочитанный фрагмент данных
                System.out.print(new String(buf_out, 0, size));
            }
        } while (true);

        //закрываем сокет
        mySocket.close();
    }
}