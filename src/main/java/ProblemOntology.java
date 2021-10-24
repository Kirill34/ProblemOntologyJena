import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.*;


import java.io.InputStream;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;
import static org.apache.jena.shacl.vocabulary.SHACLM.NS;

public class ProblemOntology {

    public static void main(String[] args)
    {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        String inputFileName="ProblemOntology.owl";
        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        // create the reasoning model using the base
        OntModel inf = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, model );
        Individual i = inf.createIndividual( inf.createOntResource("http://www.semanticweb.org/dns/ontologies/2021/9/untitled-ontology-62#FirstPhrase"));
        i.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/9/untitled-ontology-62#Phrase"));
        Property textProperty=inf.createDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/9/untitled-ontology-62/text11");
        i.addProperty(textProperty, "Первая фраза");
       // i.addLiteral(textProperty, "Первая фраза");

        // write it to standard out
        inf.write(System.out);


    }
}
