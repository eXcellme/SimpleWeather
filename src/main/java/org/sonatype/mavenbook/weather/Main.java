package org.sonatype.mavenbook.weather;
import java.io.InputStream;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
public class Main {
	/**
		Beijing	2151330
		Beijing	12578011

		D:\bflee\Maven_Prj\simple-weather>
		mvn -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=5865  exec:java -Dexec.mainClass=org.sonatype.mavenbook.weather.Main -Dexec.arguments=2151330 
	*/
		/**
	
	摄氏温度（C）和华氏温度（F）之间的换算关系为F=9/5c+32, 或C=5/9(F-32)
	*/
	private static Logger log = Logger.getLogger(Main.class);
  public static void main(String[] args) throws Exception {
  	int i = 0;
  	for(String a : args){
  		log.debug(i+":"+a);
  		}
    // Configure Log4J
    PropertyConfigurator.configure(Main.class.getClassLoader()
                                       .getResource("log4j.properties"));
		String host = System.getProperty("http.proxyHost");
		String port = System.getProperty("http.proxyPort");
		log.debug("Set proxy ok..."+host+":"+port );
    // Read the Zip Code from the Command-line (if none supplied, use 60202)
    int zipcode = 10;//60202;
    if(args!=null && args.length>0){
    	 zipcode = Integer.parseInt(args[0]);
    }
    try {
      zipcode = Integer.parseInt(args[0]);
    } catch( Exception e ) {}
    // Start the program
    log.debug("start main...zipcode is "+zipcode);
    new Main(zipcode).start();
  }
  private int zip;
  public Main(int zip) {
    this.zip = zip;
  }
  public void start() throws Exception {
    // Retrieve Data
    InputStream dataIn = new YahooRetriever().retrieve( zip );
    // Parse Data
    Weather weather = new YahooParser().parse( dataIn );
    int f = Integer.parseInt(weather.getTemp());
    int c = (f-32)*5/9;
    weather.setTemp(String.valueOf(c));
    // Format (Print) Data
    System.out.print( new WeatherFormatter().format( weather ) );
  }
}