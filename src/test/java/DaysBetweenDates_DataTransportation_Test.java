import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class DaysBetweenDates_DataTransportation_Test extends DaysBetweenDates_BaseTest{

    protected static String DATA_ELEMENTS_RULES_FILE = "rules/data_transportation.rules";
    private InfModel infModel = null;

    @BeforeEach
    public void init() throws FileNotFoundException {
        createModel(DATA_ELEMENTS_RULES_FILE);
        addCorrectDataDirections();
        addPresentation();
        infModel = ModelFactory.createInfModel(reasoner, inf);

        ResIterator iter = infModel.listResourcesWithProperty(inf.getObjectProperty(BASE_URL+"#hasData"));
        while (iter.hasNext())
        {
            Resource r = iter.nextResource();
        }
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

    private void addCorrectDataDirections()
    {
        setDataDirections(DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_READ_ONLY,DATA_TRANSFER_METHOD_WRITE_ONLY);
    }

    private void addPresentation()
    {
        //Представление для даты
        Individual dateStructPresentation = inf.createIndividual(BASE_URL+"#Date_StructPresentation", inf.getOntClass(BASE_URL+"#StructPresentation"));
        dateStructPresentation.addRDFType(OWL2.NamedIndividual);
        dateStructPresentation.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "Дата - сложная сущность, состоящая из номере дня, номера месяца и номера года");

        Individual dayOfDate_DataElement = inf.createIndividual(BASE_URL+"#DayOfDate_DataElement", inf.getOntClass(BASE_URL+"#DataElement"));
        dayOfDate_DataElement.addRDFType(OWL2.NamedIndividual);
        dayOfDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "День - элемент даты");

        dateStructPresentation.addProperty(inf.createObjectProperty(BASE_URL+"#hasStructElement") , inf.getIndividual(BASE_URL+"#Scalar_DayNumber"));

        date_Entity.addProperty(inf.createObjectProperty(BASE_URL+"#hasChoosedPresentation"), dateStructPresentation);

        //Представление для количества дней
    }

    @Test
    public void firstTest()
    {
        System.out.println("");
    }




}
