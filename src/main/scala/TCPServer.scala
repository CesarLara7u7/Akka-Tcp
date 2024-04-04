import akka.actor
import akka.actor.typed.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Tcp.ServerBinding
import akka.stream.scaladsl.{Flow, Framing, Source, Tcp}
import akka.util.ByteString
import org.slf4j.LoggerFactory

import scala.concurrent.Future

class TCPServer(){

  val logger = LoggerFactory.getLogger(getClass)
  implicit val clasicActor: actor.ActorSystem = actor.ActorSystem.apply()
  implicit val mat: Materializer = Materializer(clasicActor)

  def start(): Unit = {

    val connections: Source[Tcp.IncomingConnection, Future[ServerBinding]] = {
      logger.info("Server start")
      Tcp(clasicActor).bind("localhost", 8888)
    }
    connections.runForeach { connection =>
      logger.info("new connection: {}", connection.remoteAddress)
      val logMessageHandling = Flow[ByteString]
        .via(
          Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true)
        ) //Se definen las reglas del mensaje
        .map(_.utf8String)
        .map(message => {
          logger.info("Message: {}", message);
          //TODO: MANEJO DEL MENSAJE, IMPORTAR LAS CLASES QUE MANEJEN LOS LOGS
          "Mensaje: " + message + " recibido!!!\n"//Es la respuesta del servidor al cliente
        })
        .map(ByteString(_))
      connection.handleWith(logMessageHandling)
    }
  }
}

//      val connection: Flow[ByteString, ByteString, Future[Tcp.OutgoingConnection]] =
//        Tcp(actorSystem).outgoingConnection("localhost", 8888)
//
//      val replParser =
//        Flow[String]
//          .takeWhile(_ != "q")
//          .concat(Source.single("WAZAAAAAAAAA"))
//          .map(elem => ByteString(s"$elem\n"))
//
//      val repl = Flow[ByteString]
//        .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256, allowTruncation = true))
//        .map(_.utf8String)
//        .map(text => {
//          actorSystem.log.info("Server: {}", text)
//          text
//        })
//        .map(_ => readLine("> "))
//        .via(replParser)
//
//      val connected = connection.join(repl).run()
//      connected.onComplete {
//        case Failure(exception) =>
//          actorSystem.log.info("Error: {}", exception)
//        case Success(value) =>
//          actorSystem.log.info("COMPLETADO: {}", value)
//      }