import org.apache.jena.base.Sys;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL2;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.HashMap;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;

public class DaysBetweenDates_DataDirection_Test extends DaysBetweenDates_BaseTest
{
    protected HashMap<Individual,String> expectedIndividualMessages = new HashMap<Individual,String>();

    @BeforeEach
    public void init() throws FileNotFoundException {
        createModel(DATA_DIRECTION_RULES);
    }

    private void setDataDirections(String FirstDateDirection, String SecondDateDirection, String DaysCountDirection)
    {
        //Представление элементов данных (Задает студент)
        //Период в днях
        Individual daysCount_Presentation = inf.createIndividual(BASE_URL + "#DaysCount_Presentation", inf.createOntResource(BASE_URL + "#DataElementPresentation"));
        daysCount_Presentation.addRDFType(OWL2.NamedIndividual);
        daysCount_Presentation.addProperty(inf.createDatatypeProperty(BASE_URL+"#transferMethod"), DaysCountDirection);
        daysCount.addProperty(inf.createObjectProperty(BASE_URL+"#hasPresentation"), daysCount_Presentation);

        //Первая дата
        Individual firsDate_Presentation = inf.createIndividual(BASE_URL + "#FirstDate_Presentation", inf.createOntResource(BASE_URL + "#DataElementPresentation"));
        firsDate_Presentation.addRDFType(OWL2.NamedIndividual);
        firsDate_Presentation.addProperty(inf.createDatatypeProperty(BASE_URL+"#transferMethod"), FirstDateDirection);
        firstDate_DataElement.addProperty(inf.createObjectProperty(BASE_URL+"#hasPresentation"), firsDate_Presentation);

        //Вторая дата
        Individual secondDate_Presentation = inf.createIndividual(BASE_URL + "#SecondDate_Presentation", inf.createOntResource(BASE_URL + "#DataElementPresentation"));
        secondDate_Presentation.addRDFType(OWL2.NamedIndividual);
        secondDate_Presentation.addProperty(inf.createDatatypeProperty(BASE_URL+"#transferMethod"), SecondDateDirection);
        secondDate_DataElement.addProperty(inf.createObjectProperty(BASE_URL+"#hasPresentation"), secondDate_Presentation);
    }

    @Test
    public void Test_CorrectDataDirections()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_RETURN);
    }

    @Test
    public void Test_ReturnFirstDate()
    {
        setDataDirections(DATA_TRANSFER_METHOD_RETURN,DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Возвращаемое значение вместо входного");
    }

    @Test
    public void Test_WriteFirstDate()
    {
        setDataDirections(DATA_TRANSFER_METHOD_WRITE_ONLY,DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Выходное значение вместо входного");
    }

    @Test
    public void Test_ReadWriteFirstDate()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_WRITE,DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Обновляемое значение вместо входного");
    }

    @Test
    public void Test_ReadDaysCount()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_ONLY, DATA_TRANSFER_METHOD_READ_ONLY, DATA_TRANSFER_METHOD_READ_ONLY);
        expectedIndividualMessages.put(this.daysCount, "Входное значение вместо выходного");
    }

    @Test
    public void Test_ReadWriteDaysCount()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_ONLY, DATA_TRANSFER_METHOD_READ_ONLY, DATA_TRANSFER_METHOD_READ_WRITE);
        expectedIndividualMessages.put(this.daysCount, "Обновляемое значение вместо выходного");
    }

    @Test
    public void Test_WriteSecondDate()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_ONLY, DATA_TRANSFER_METHOD_WRITE_ONLY, DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.secondDate_DataElement, "Выходное значение вместо входного");
    }

    @Test
    public void Test_WriteTwoDates()
    {
        setDataDirections(DATA_TRANSFER_METHOD_WRITE_ONLY, DATA_TRANSFER_METHOD_WRITE_ONLY, DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Выходное значение вместо входного");
        expectedIndividualMessages.put(this.secondDate_DataElement, "Выходное значение вместо входного");
    }

    @Test
    public void Test_UpdateTwoDates()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_WRITE, DATA_TRANSFER_METHOD_READ_WRITE, DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Обновляемое значение вместо входного");
        expectedIndividualMessages.put(this.secondDate_DataElement, "Обновляемое значение вместо входного");
    }

    @Test
    public void Test_WriteFirstDate_UpdateSecondDate()
    {
        setDataDirections(DATA_TRANSFER_METHOD_WRITE_ONLY, DATA_TRANSFER_METHOD_READ_WRITE, DATA_TRANSFER_METHOD_RETURN);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Выходное значение вместо входного");
        expectedIndividualMessages.put(this.secondDate_DataElement, "Обновляемое значение вместо входного");
    }

    @Test
    public void Test_WriteFirstDate_UpdateSecondDate_ReadDaysCount()
    {
        setDataDirections(DATA_TRANSFER_METHOD_WRITE_ONLY, DATA_TRANSFER_METHOD_READ_WRITE, DATA_TRANSFER_METHOD_READ_ONLY);
        expectedIndividualMessages.put(this.firstDate_DataElement, "Выходное значение вместо входного");
        expectedIndividualMessages.put(this.secondDate_DataElement, "Обновляемое значение вместо входного");
        expectedIndividualMessages.put(this.daysCount, "Входное значение вместо выходного");
    }

    @AfterEach
    public void setDown()
    {
        HashMap<Individual,String> realIndiviadualMessages = new HashMap<Individual,String>();
        InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        ResIterator iter = infModel.listResourcesWithProperty(infModel.getProperty(BASE_URL+"#hasError"));

        while (iter.hasNext()) {
            Resource r = iter.nextResource();
            Property hasError_Property = infModel.getProperty(BASE_URL+"#hasError");
            Statement value_statement = r.getProperty(hasError_Property);
            Individual ind = inf.getIndividual((r.getURI()));
            realIndiviadualMessages.put((Individual) ind, value_statement.getString());
        }
        Assertions.assertEquals(expectedIndividualMessages, realIndiviadualMessages);

        iter = infModel.listResourcesWithProperty(infModel.getProperty(BASE_URL+"#hasFullText"));
        while (iter.hasNext())
        {
            Resource r = iter.nextResource();
            Property hasError_Property = infModel.getProperty(BASE_URL+"#hasFullText");
            Statement value_statement = r.getProperty(hasError_Property);
            String fullText = value_statement.getString();
            System.out.print("Текст задания: "+fullText+"\n");
        }



    }
}
