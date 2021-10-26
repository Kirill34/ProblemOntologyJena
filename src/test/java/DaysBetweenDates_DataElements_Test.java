import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DaysBetweenDates_DataElements_Test extends  DaysBetweenDates_BaseTest{

    protected static String DATA_ELEMENTS_RULES_FILE = "rules/data_elements.rules";

    @BeforeEach
    public void init() throws FileNotFoundException {
        createModel(DATA_ELEMENTS_RULES_FILE);
    }

    @Test
    public void firstTest()
    {

    }

    @AfterEach
    public void setDown()
    {
        InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        ResIterator iter = infModel.listResourcesWithProperty(infModel.getProperty(BASE_URL+"#fullName"));

        while (iter.hasNext()) {
            Resource r = iter.nextResource();
            Property hasError_Property = infModel.getProperty(BASE_URL+"#fullName");
            Statement value_statement = r.getProperty(hasError_Property);
            System.out.print(value_statement.getString()+"\n");
            //Individual ind = inf.getIndividual((r.getURI()));

        }
    }


}
