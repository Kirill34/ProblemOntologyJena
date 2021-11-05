import org.apache.jena.base.Sys;
import org.apache.jena.ontology.Individual;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.lang.SPARQLParser;
import org.apache.jena.vocabulary.OWL2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.Writer;

public class DaysBetweenDates_DataTransportation_Test extends DaysBetweenDates_BaseTest{

    protected static String DATA_ELEMENTS_RULES_FILE = "rules/data_transportation.rules";
    private InfModel infModel = null;

    @BeforeEach
    public void init() throws FileNotFoundException {
        createModel(DATA_ELEMENTS_RULES_FILE);
        addCorrectDataDirections();
        addPresentation();

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
        //inf.createObjectProperty(BASE_URL+"#hasReturnValue");
        String queryString =// "SET  {?problem <http://www.semanticweb.org/problem-ontology#hasReturnValue> ?parameter }" +
                "SELECT ?problem ?parameter ?input_data " +
                "WHERE {" +
                " { ?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter; } " +
                "{ ?problem <http://www.semanticweb.org/problem-ontology#hasInputData> ?input_data ; }" +
                " ?parameter <http://www.semanticweb.org/problem-ontology#fromDataElement> ?input_data . " +
                "} " +
                        //"INSERT INTO <http://www.semanticweb.org/problem-ontology>  {?problem <http://www.semanticweb.org/problem-ontology#hasReturnValue> ?parameter }"+
                "";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet resultSet = qExec.execSelect();
        while (resultSet.hasNext())
        {
            QuerySolution querySolution = resultSet.next();
            Resource problem =  querySolution.get("?problem").asResource();
            Resource parameter = querySolution.get("?parameter").asResource();
            Resource input_data = querySolution.get("?input_data").asResource();

            problem.addProperty(infModel.createProperty(BASE_URL+"#hasReturnValue"), parameter);
            break;

        }
        //ResultSetFormatter.out(qExec.execSelect());
    }

    @AfterEach
    public void setDown()
    {
        System.out.println("Вывод результатов:");
        infModel = ModelFactory.createInfModel(reasoner, infModel);


        String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasReturnValue> ?parameter }";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSetFormatter.out(qExec.execSelect());
    }




}
