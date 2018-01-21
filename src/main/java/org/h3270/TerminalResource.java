package org.h3270;

import org.h3270.host.S3270.TerminalMode;
import org.h3270.host.S3270.TerminalType;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.f3270.Terminal;

/**
 * Create a TerminalResource for JUnit Access.
 * 
 * @author doerges
 *
 */
public class TerminalResource extends ExternalResource {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TerminalResource.class);

	/**
	 * Terminal
	 */
	private Terminal driver;

	/**
	 * specify path to client, default to windows version
	 */
	private String pathToClient = "s3270/client/ws3270.exe";

	/**
	 * Host-Terminal
	 */
	private String host;

	/**
	 * Port-Terminal
	 */
	private int port;

	/**
	 * terminal mode: screen-size, use @see TerminalMode
	 */
	private TerminalMode mode;

	/**
	 * terminal type: monochrome or color, @see TerminalType
	 */
	private TerminalType type;

	/**
	 * show terminal during testrun if set to true
	 */
	private boolean showTerminal;

	/**
	 * show incoming and outgoing api calls
	 */
	private boolean debug;

	/**
	 * Standard constructor
	 */
	public TerminalResource() {
		host = "";
		port = 0;
	}

	/**
	 * FluentInterface: Define host
	 * 
	 * @param aHost
	 * @return
	 */
	public TerminalResource withHost(String aHost) {
		this.host = aHost;
		return this;
	}

	/**
	 * FluentInterface: Define port
	 * 
	 * @param aPort
	 * @return
	 */
	public TerminalResource withPort(int aPort) {
		this.port = aPort;
		return this;
	}

	/**
	 * FluentInterface: Define TerminalMode
	 * 
	 * @param aCodePage
	 * @return
	 */
	public TerminalResource withMode(TerminalMode aMode) {
		this.mode = aMode;
		return this;
	}

	/**
	 * FluentInterface: Define Type
	 * 
	 * @param aType
	 * @return
	 */
	public TerminalResource withType(TerminalType aType) {
		this.type = aType;
		return this;
	}

	/**
	 * FluentInterface: Hold connection after TestCase.
	 * 
	 * @param aDecision
	 * @return
	 */
	public TerminalResource showTerminalWindow(boolean isVisible) {
		this.showTerminal = isVisible;
		return this;
	}

	/**
	 * FluentInterface: control debug output
	 * 
	 * @param isDebug
	 * @return
	 */
	public TerminalResource setDebug(boolean isDebug) {
		this.debug = isDebug;
		return this;
	}

	public TerminalResource pathToClient(String aPath) {
		this.pathToClient = aPath;
		return this;
	}

	/**
	 * initialize resource before testcase.
	 */
	@Override
	protected void before() throws Throwable {
		connect();
	}

	public void connect() throws Throwable {
		this.driver = new Terminal(this.pathToClient, this.host, this.port, this.type, this.mode, this.showTerminal);
		this.driver.connect();
	}

	/**
	 * Clean up resource after testcase.
	 */
	@Override
	protected void after() {
		disconnect();
	}

	public void disconnect() {
		this.driver.disconnect();
	}

	/**
	 * Get driver instance.
	 * 
	 * @return
	 */
	public Terminal getDriver() {
		return this.driver;
	}
}