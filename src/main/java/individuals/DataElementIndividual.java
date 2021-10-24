package individuals;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.*;



public class DataElementIndividual implements Individual{

    @Override
    public void setOntClass(Resource resource) {

    }

    @Override
    public void addOntClass(Resource resource) {

    }

    @Override
    public OntClass getOntClass() {
        return null;
    }

    @Override
    public OntClass getOntClass(boolean b) {
        return null;
    }

    @Override
    public <T extends OntClass> ExtendedIterator<T> listOntClasses(boolean b) {
        return null;
    }

    @Override
    public boolean hasOntClass(Resource resource, boolean b) {
        return false;
    }

    @Override
    public boolean hasOntClass(Resource resource) {
        return false;
    }

    @Override
    public boolean hasOntClass(String s) {
        return false;
    }

    @Override
    public void removeOntClass(Resource resource) {

    }

    @Override
    public OntModel getOntModel() {
        return null;
    }

    @Override
    public Profile getProfile() {
        return null;
    }

    @Override
    public boolean isOntLanguageTerm() {
        return false;
    }

    @Override
    public void setSameAs(Resource resource) {

    }

    @Override
    public void addSameAs(Resource resource) {

    }

    @Override
    public OntResource getSameAs() {
        return null;
    }

    @Override
    public ExtendedIterator<? extends Resource> listSameAs() {
        return null;
    }

    @Override
    public boolean isSameAs(Resource resource) {
        return false;
    }

    @Override
    public void removeSameAs(Resource resource) {

    }

    @Override
    public void setDifferentFrom(Resource resource) {

    }

    @Override
    public void addDifferentFrom(Resource resource) {

    }

    @Override
    public OntResource getDifferentFrom() {
        return null;
    }

    @Override
    public ExtendedIterator<? extends Resource> listDifferentFrom() {
        return null;
    }

    @Override
    public boolean isDifferentFrom(Resource resource) {
        return false;
    }

    @Override
    public void removeDifferentFrom(Resource resource) {

    }

    @Override
    public void setSeeAlso(Resource resource) {

    }

    @Override
    public void addSeeAlso(Resource resource) {

    }

    @Override
    public Resource getSeeAlso() {
        return null;
    }

    @Override
    public ExtendedIterator<RDFNode> listSeeAlso() {
        return null;
    }

    @Override
    public boolean hasSeeAlso(Resource resource) {
        return false;
    }

    @Override
    public void removeSeeAlso(Resource resource) {

    }

    @Override
    public void setIsDefinedBy(Resource resource) {

    }

    @Override
    public void addIsDefinedBy(Resource resource) {

    }

    @Override
    public Resource getIsDefinedBy() {
        return null;
    }

    @Override
    public ExtendedIterator<RDFNode> listIsDefinedBy() {
        return null;
    }

    @Override
    public boolean isDefinedBy(Resource resource) {
        return false;
    }

    @Override
    public void removeDefinedBy(Resource resource) {

    }

    @Override
    public void setVersionInfo(String s) {

    }

    @Override
    public void addVersionInfo(String s) {

    }

    @Override
    public String getVersionInfo() {
        return null;
    }

    @Override
    public ExtendedIterator<String> listVersionInfo() {
        return null;
    }

    @Override
    public boolean hasVersionInfo(String s) {
        return false;
    }

    @Override
    public void removeVersionInfo(String s) {

    }

    @Override
    public void setLabel(String s, String s1) {

    }

    @Override
    public void addLabel(String s, String s1) {

    }

    @Override
    public void addLabel(Literal literal) {

    }

    @Override
    public String getLabel(String s) {
        return null;
    }

    @Override
    public ExtendedIterator<RDFNode> listLabels(String s) {
        return null;
    }

    @Override
    public boolean hasLabel(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasLabel(Literal literal) {
        return false;
    }

    @Override
    public void removeLabel(String s, String s1) {

    }

    @Override
    public void removeLabel(Literal literal) {

    }

    @Override
    public void setComment(String s, String s1) {

    }

    @Override
    public void addComment(String s, String s1) {

    }

    @Override
    public void addComment(Literal literal) {

    }

    @Override
    public String getComment(String s) {
        return null;
    }

    @Override
    public ExtendedIterator<RDFNode> listComments(String s) {
        return null;
    }

    @Override
    public boolean hasComment(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasComment(Literal literal) {
        return false;
    }

    @Override
    public void removeComment(String s, String s1) {

    }

    @Override
    public void removeComment(Literal literal) {

    }

    @Override
    public void setRDFType(Resource resource) {

    }

    @Override
    public void addRDFType(Resource resource) {

    }

    @Override
    public Resource getRDFType() {
        return null;
    }

    @Override
    public Resource getRDFType(boolean b) {
        return null;
    }

    @Override
    public ExtendedIterator<Resource> listRDFTypes(boolean b) {
        return null;
    }

    @Override
    public boolean hasRDFType(Resource resource, boolean b) {
        return false;
    }

    @Override
    public boolean hasRDFType(Resource resource) {
        return false;
    }

    @Override
    public void removeRDFType(Resource resource) {

    }

    @Override
    public boolean hasRDFType(String s) {
        return false;
    }

    @Override
    public int getCardinality(Property property) {
        return 0;
    }

    @Override
    public void setPropertyValue(Property property, RDFNode rdfNode) {

    }

    @Override
    public RDFNode getPropertyValue(Property property) {
        return null;
    }

    @Override
    public NodeIterator listPropertyValues(Property property) {
        return null;
    }

    @Override
    public void removeProperty(Property property, RDFNode rdfNode) {

    }

    @Override
    public void remove() {

    }

    @Override
    public OntProperty asProperty() {
        return null;
    }

    @Override
    public AnnotationProperty asAnnotationProperty() {
        return null;
    }

    @Override
    public ObjectProperty asObjectProperty() {
        return null;
    }

    @Override
    public DatatypeProperty asDatatypeProperty() {
        return null;
    }

    @Override
    public Individual asIndividual() {
        return null;
    }

    @Override
    public OntClass asClass() {
        return null;
    }

    @Override
    public Ontology asOntology() {
        return null;
    }

    @Override
    public DataRange asDataRange() {
        return null;
    }

    @Override
    public AllDifferent asAllDifferent() {
        return null;
    }

    @Override
    public boolean isProperty() {
        return false;
    }

    @Override
    public boolean isAnnotationProperty() {
        return false;
    }

    @Override
    public boolean isObjectProperty() {
        return false;
    }

    @Override
    public boolean isDatatypeProperty() {
        return false;
    }

    @Override
    public boolean isIndividual() {
        return false;
    }

    @Override
    public boolean isClass() {
        return false;
    }

    @Override
    public boolean isOntology() {
        return false;
    }

    @Override
    public boolean isDataRange() {
        return false;
    }

    @Override
    public boolean isAllDifferent() {
        return false;
    }

    @Override
    public AnonId getId() {
        return null;
    }

    @Override
    public boolean isAnon() {
        return false;
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public boolean isURIResource() {
        return false;
    }

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public boolean isStmtResource() {
        return false;
    }

    @Override
    public <T extends RDFNode> T as(Class<T> aClass) {
        return null;
    }

    @Override
    public <T extends RDFNode> boolean canAs(Class<T> aClass) {
        return false;
    }

    @Override
    public Model getModel() {
        return null;
    }

    @Override
    public Resource inModel(Model model) {
        return null;
    }

    @Override
    public Object visitWith(RDFVisitor rdfVisitor) {
        return null;
    }

    @Override
    public Resource asResource() {
        return null;
    }

    @Override
    public Literal asLiteral() {
        return null;
    }

    @Override
    public boolean hasURI(String s) {
        return false;
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public Statement getStmtTerm() {
        return null;
    }

    @Override
    public String getNameSpace() {
        return null;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public Statement getRequiredProperty(Property property) {
        return null;
    }

    @Override
    public Statement getRequiredProperty(Property property, String s) {
        return null;
    }

    @Override
    public Statement getProperty(Property property) {
        return null;
    }

    @Override
    public Statement getProperty(Property property, String s) {
        return null;
    }

    @Override
    public StmtIterator listProperties(Property property) {
        return null;
    }

    @Override
    public StmtIterator listProperties(Property property, String s) {
        return null;
    }

    @Override
    public StmtIterator listProperties() {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, boolean b) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, long l) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, char c) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, double v) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, float v) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, Object o) {
        return null;
    }

    @Override
    public Resource addLiteral(Property property, Literal literal) {
        return null;
    }

    @Override
    public Resource addProperty(Property property, String s) {
        return null;
    }

    @Override
    public Resource addProperty(Property property, String s, String s1) {
        return null;
    }

    @Override
    public Resource addProperty(Property property, String s, RDFDatatype rdfDatatype) {
        return null;
    }

    @Override
    public Resource addProperty(Property property, RDFNode rdfNode) {
        return null;
    }

    @Override
    public boolean hasProperty(Property property) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, boolean b) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, long l) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, char c) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, double v) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, float v) {
        return false;
    }

    @Override
    public boolean hasLiteral(Property property, Object o) {
        return false;
    }

    @Override
    public boolean hasProperty(Property property, String s) {
        return false;
    }

    @Override
    public boolean hasProperty(Property property, String s, String s1) {
        return false;
    }

    @Override
    public boolean hasProperty(Property property, RDFNode rdfNode) {
        return false;
    }

    @Override
    public Resource removeProperties() {
        return null;
    }

    @Override
    public Resource removeAll(Property property) {
        return null;
    }

    @Override
    public Resource begin() {
        return null;
    }

    @Override
    public Resource abort() {
        return null;
    }

    @Override
    public Resource commit() {
        return null;
    }

    @Override
    public Resource getPropertyResourceValue(Property property) {
        return null;
    }

    @Override
    public Node asNode() {
        return null;
    }
}
