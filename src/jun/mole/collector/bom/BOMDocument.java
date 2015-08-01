package jun.mole.collector.bom;

import java.net.MalformedURLException;

import org.w3c.dom.*;

public class BOMDocument {
	private Document doc;
	public BOMLocation location;

	public BOMDocument(Document d) {
		doc = d;
		try {
			location = new BOMLocation(doc.getDocumentURI());
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}

	public Node[] getElementsByTagName(String tagname) {
		NodeList nodelist = doc.getElementsByTagName(tagname);
		Node[] ret = new Node[nodelist.getLength()];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = nodelist.item(i);
		}
		return ret;
	}

	public Element createElement(String tagName) throws DOMException {
		return doc.createElement(tagName);
	}

	public Element getElementById(String id) {
		return doc.getElementById(id);
	}

	public Node getFirstChild() {
		return doc.getFirstChild();
	}

	public Element getDocumentElement() {
		return doc.getDocumentElement();
	}

	public String getNodeName() {
		return doc.getNodeName();
	}

	public String getNodeValue() throws DOMException {
		return doc.getNodeValue();
	}

	public void setNodeValue(String nodeValue) throws DOMException {
		doc.setNodeValue(nodeValue);

	}

	public short getNodeType() {
		return doc.getNodeType();
	}

	public Node getParentNode() {
		return doc.getParentNode();
	}

	public Node[] getChildNodes() {
		NodeList nodeList = doc.getChildNodes();
		Node[] ret = new Node[nodeList.getLength()];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = nodeList.item(i);
		}
		return ret;
	}

	public Node getLastChild() {
		return doc.getLastChild();
	}

	public Node getPreviousSibling() {
		return doc.getPreviousSibling();
	}

	public Node getNextSibling() {
		return doc.getNextSibling();
	}

	public NamedNodeMap getAttributes() {
		return doc.getAttributes();
	}

	public Document getOwnerDocument() {
		return doc.getOwnerDocument();
	}

	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		return doc.insertBefore(newChild, refChild);
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		return doc.replaceChild(newChild, oldChild);
	}

	public Node removeChild(Node oldChild) throws DOMException {
		return doc.removeChild(oldChild);
	}

	public Node appendChild(Node newChild) throws DOMException {
		return doc.appendChild(newChild);
	}

	public boolean hasChildNodes() {
		return doc.hasChildNodes();
	}

	public Node cloneNode(boolean deep) {
		return doc.cloneNode(deep);
	}

	public void normalize() {
		doc.normalize();

	}

	public boolean isSupported(String feature, String version) {
		return doc.isSupported(feature, version);
	}

	public String getNamespaceURI() {
		return doc.getNamespaceURI();
	}

	public String getPrefix() {
		return doc.getPrefix();
	}

	public void setPrefix(String prefix) throws DOMException {
		doc.setPrefix(prefix);

	}

	public String getLocalName() {
		return doc.getLocalName();
	}

	public boolean hasAttributes() {
		return doc.hasAttributes();
	}

	public String getBaseURI() {
		return doc.getBaseURI();
	}

	public short compareDocumentPosition(Node other) throws DOMException {
		return doc.compareDocumentPosition(other);
	}

	public String getTextContent() throws DOMException {
		return doc.getTextContent();
	}

	public void setTextContent(String textContent) throws DOMException {
		doc.setTextContent(textContent);

	}

	public boolean isSameNode(Node other) {
		return doc.isSameNode(other);
	}

	public String lookupPrefix(String namespaceURI) {
		return doc.lookupPrefix(namespaceURI);
	}

	public boolean isDefaultNamespace(String namespaceURI) {
		return doc.isDefaultNamespace(namespaceURI);
	}

	public String lookupNamespaceURI(String prefix) {
		return doc.lookupNamespaceURI(prefix);
	}

	public boolean isEqualNode(Node arg) {
		return doc.isEqualNode(arg);
	}

	public Object getFeature(String feature, String version) {
		return doc.getFeature(feature, version);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return doc.setUserData(key, data, handler);
	}

	public Object getUserData(String key) {
		return doc.getUserData(key);
	}

	public DocumentType getDoctype() {
		return doc.getDoctype();
	}

	public DOMImplementation getImplementation() {
		return doc.getImplementation();
	}

	public DocumentFragment createDocumentFragment() {
		return doc.createDocumentFragment();
	}

	public Text createTextNode(String data) {
		return doc.createTextNode(data);
	}

	public Comment createComment(String data) {
		return doc.createComment(data);
	}

	public CDATASection createCDATASection(String data) throws DOMException {
		return doc.createCDATASection(data);
	}

	public ProcessingInstruction createProcessingInstruction(String target,
			String data) throws DOMException {
		return doc.createProcessingInstruction(target, data);
	}

	public Attr createAttribute(String name) throws DOMException {
		return doc.createAttribute(name);
	}

	public EntityReference createEntityReference(String name)
			throws DOMException {
		return doc.createEntityReference(name);
	}

	public Node importNode(Node importedNode, boolean deep) throws DOMException {
		return doc.importNode(importedNode, deep);
	}

	public Element createElementNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		return doc.createElementNS(namespaceURI, qualifiedName);
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		return doc.createAttributeNS(namespaceURI, qualifiedName);
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
		return doc.getElementsByTagNameNS(namespaceURI, localName);
	}

	public String getInputEncoding() {
		return doc.getInputEncoding();
	}

	public String getXmlEncoding() {
		return doc.getXmlEncoding();
	}

	public boolean getXmlStandalone() {
		return doc.getXmlStandalone();
	}

	public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
		doc.setXmlStandalone(xmlStandalone);

	}

	public String getXmlVersion() {
		return doc.getXmlVersion();
	}

	public void setXmlVersion(String xmlVersion) throws DOMException {
		doc.setXmlVersion(xmlVersion);

	}

	public boolean getStrictErrorChecking() {
		return doc.getStrictErrorChecking();
	}

	public void setStrictErrorChecking(boolean strictErrorChecking) {
		doc.setStrictErrorChecking(strictErrorChecking);

	}

	public String getDocumentURI() {
		return doc.getDocumentURI();
	}

	public void setDocumentURI(String documentURI) {
		doc.setDocumentURI(documentURI);

	}

	public Node adoptNode(Node source) throws DOMException {
		return doc.adoptNode(source);
	}

	public DOMConfiguration getDomConfig() {
		return doc.getDomConfig();
	}

	public void normalizeDocument() {
		doc.normalizeDocument();

	}

	public Node renameNode(Node n, String namespaceURI, String qualifiedName)
			throws DOMException {
		return doc.renameNode(n, namespaceURI, qualifiedName);
	}

}
