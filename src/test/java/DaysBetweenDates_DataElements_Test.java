import com.sun.org.apache.bcel.internal.generic.Select;
import org.apache.jena.base.Sys;
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
    private InfModel infModel = null;

    @BeforeEach
    public void init() throws FileNotFoundException {
        createModel(DATA_ELEMENTS_RULES_FILE);
        infModel = ModelFactory.createInfModel(reasoner, inf);
    }

    @Test
    public void entityDatePresentation()
    {

//        Resource entityDate = inf.getResource(BASE_URL+"#Entity_Date");
//        Property property_presentationOf = infModel.getProperty(BASE_URL+"#presentationOf");
//        Selector selector = new SimpleSelector(entityDate, property_presentationOf, );
//        infModel.query();


        ResIterator iter = infModel.listResourcesWithProperty(infModel.getProperty(BASE_URL+"#presentationOf"));
        while (iter.hasNext())
        {
            Resource r = iter.next();
            System.out.print(r+"\n");

            String presText = r.getProperty( infModel.getProperty(BASE_URL+"#text")).getString();
            System.out.println(presText);

            String elCount = r.getProperty( infModel.getProperty(BASE_URL+"#elementCount")).getString();
            System.out.println("Количество элементов: "+elCount);

            Statement statement = r.getProperty(infModel.getProperty(BASE_URL+"#presentationOf"));
            System.out.print(statement.getResource()+"\n");

        }

    }

    @Test
    public void firstTest()
    {

    }

    @AfterEach
    public void setDown()
    {

    }


}
