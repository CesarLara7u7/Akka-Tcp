

object MainApp {

  def main(args: Array[String]): Unit = {
    val TCPServer = new TCPServer()
    TCPServer.start()
  }

}
