import akka.actor.typed.{ActorSystem, Behavior}

object MainApp {

  def main(args: Array[String]): Unit = {
    val TCPServer = new TCPServer()
    TCPServer.start()
  }

}
