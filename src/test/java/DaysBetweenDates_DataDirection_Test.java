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

public class DaysBetweenDates_DataDirection_Test
{
    private Model model = null;
    private OntModel inf = null;
    Reasoner reasoner = null;

    private static String BASE_URL = "http://www.semanticweb.org/problem-ontology";
    private static String ONTOLOGY_FILE = "ProblemOntology.owl";
    private static String DATA_DIRECTION_RULES = "rules/data_direction.rules";

    private static String DATA_TRANSFER_METHOD_RETURN = "return";
    private static String DATA_TRANSFER_METHOD_READ_ONLY = "read-only";
    private static String DATA_TRANSFER_METHOD_WRITE_ONLY = "write-only";
    private static String DATA_TRANSFER_METHOD_READ_WRITE = "read-write";



    private Individual daysCount = null;
    private Individual firstDate_DataElement = null;
    private Individual secondDate_DataElement = null;

    private HashMap<Individual,String> expectedIndividualMessages = new HashMap<Individual,String>();

    public static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder(512);
        try {
            Reader r = new InputStreamReader(is, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    @BeforeEach
    public void init() throws FileNotFoundException {
        // create an empty model
        model = ModelFactory.createDefaultModel();
        String inputFileName=ONTOLOGY_FILE;
        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

      //  File file = new File("TODO");
        InputStream stream = getClass().getClassLoader().getResourceAsStream(DATA_DIRECTION_RULES);
        String rules = DaysBetweenDates_DataDirection_Test.readStream( stream);
        //System.out.print(rules);
        reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        reasoner.setDerivationLogging(true);
        reasoner.setDerivationLogging(true);
        reasoner.bindSchema(model);


        inf = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, model);

        //Фраза "Количество дней"
        Individual daysCount_phrase = inf.createIndividual(BASE_URL+"#Phrase_DaysCount", inf.getOntClass(BASE_URL+"#Phrase"));
        daysCount_phrase.addRDFType(OWL2.NamedIndividual);
        Property textProperty=inf.createDatatypeProperty(BASE_URL+"#text");
        daysCount_phrase.addProperty(textProperty, "Количество дней");

        daysCount = inf.createIndividual(BASE_URL + "#DataElement_DaysCount", inf.createOntResource(BASE_URL+"#DataElement"));
        daysCount.addRDFType(OWL2.NamedIndividual);
        daysCount.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "Количество дней");
        daysCount.addProperty(inf.createDatatypeProperty(BASE_URL+"#mission"), "Количество дней между двумя датами");

        daysCount_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#describe"), daysCount);

        //Фраза "между"
        Individual between_phrase = inf.createIndividual(BASE_URL+"#Phrase_Between", inf.getOntClass(BASE_URL+"#Phrase"));
        between_phrase.addRDFType(OWL2.NamedIndividual);
        between_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "между");


        //Фраза "первой датой"
        Individual firstDate_phrase = inf.createIndividual(BASE_URL+"#Phrase_FirstDate", inf.getOntClass(BASE_URL+"#Phrase"));
        firstDate_phrase.addRDFType(OWL2.NamedIndividual);
        firstDate_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "первой датой");

        firstDate_DataElement = inf.createIndividual(BASE_URL + "#DataElement_FirstDate", inf.createOntResource(BASE_URL+"#DataElement"));
        firstDate_DataElement.addRDFType(OWL2.NamedIndividual);
        firstDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "первая дата");
        firstDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#mission"), "первая дата");

        firstDate_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#describe"), firstDate_DataElement);

        //Фраза "и"
        Individual and_phrase = inf.createIndividual(BASE_URL+"#Phrase_And", inf.getOntClass(BASE_URL+"#Phrase"));
        and_phrase.addRDFType(OWL2.NamedIndividual);
        and_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "и");

        //Фраза "второй датой"
        Individual secondDate_phrase = inf.createIndividual(BASE_URL+"#Phrase_SecondDate", inf.getOntClass(BASE_URL+"#Phrase"));
        secondDate_phrase.addRDFType(OWL2.NamedIndividual);
        secondDate_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "второй датой");

        secondDate_DataElement = inf.createIndividual(BASE_URL + "#DataElement_SecondDate", inf.createOntResource(BASE_URL+"#DataElement"));
        secondDate_DataElement.addRDFType(OWL2.NamedIndividual);
        secondDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "вторая дата");
        secondDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#mission"), "вторая дата");

        //
        //Тип предметной области "Дата"
        Individual date_Entity = inf.createIndividual(BASE_URL + "Entity_Date", inf.createOntResource(BASE_URL + "#Entity"));
        date_Entity.addRDFType(OWL2.NamedIndividual);
        date_Entity.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "дата");


        //Тип предметной области "Период"
        Individual daysPeriod_Scalar = inf.createIndividual(BASE_URL + "#Scalar_DaysPeriod", inf.createOntResource(BASE_URL + "#Scalar"));
        daysPeriod_Scalar.addRDFType(OWL2.NamedIndividual);
        daysPeriod_Scalar.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "интервал в днях");

        //Тип предметной области "Номер дня"
        Individual dayNumber_Scalar = inf.createIndividual(BASE_URL + "#Scalar_DayNumber", inf.createOntResource(BASE_URL + "#Scalar"));
        dayNumber_Scalar.addRDFType(OWL2.NamedIndividual);
        dayNumber_Scalar.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "номер дня месяца");

        //Тип предметной области "Номер месяца"
        Individual monthNumber_Scalar = inf.createIndividual(BASE_URL + "#Scalar_MonthNumber", inf.createOntResource(BASE_URL + "#Scalar"));
        monthNumber_Scalar.addRDFType(OWL2.NamedIndividual);
        monthNumber_Scalar.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "номер месяца в году");

        //Тип предметной области "Номер года"
        Individual yearNumber_Scalar = inf.createIndividual(BASE_URL + "#Scalar_YearNumber", inf.createOntResource(BASE_URL + "#Scalar"));
        yearNumber_Scalar.addRDFType(OWL2.NamedIndividual);
        yearNumber_Scalar.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "номер года");

        //Компоненты даты




        //Сущность самой задачи
        Individual problem = inf.createIndividual(BASE_URL + "#Problem_Individual", inf.createOntResource(BASE_URL + "#Problem"));
        problem.addRDFType(OWL2.NamedIndividual);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasFormulation"), daysCount_phrase);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasInputData"), firstDate_DataElement);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasInputData"), secondDate_DataElement);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasOutputData"), daysCount);


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
    }
}
