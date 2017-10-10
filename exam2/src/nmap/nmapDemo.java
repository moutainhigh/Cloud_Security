package nmap;
import org.nmap4j.Nmap4j;
import org.nmap4j.data.NMapRun;
import org.nmap4j.data.host.Address;
import org.nmap4j.data.host.Hostnames;
import org.nmap4j.data.nmaprun.Host;

public class nmapDemo {
	public static void main(String[] args) {
		Nmap4j nmap4j = new Nmap4j(args[0]);
		
	}
}
