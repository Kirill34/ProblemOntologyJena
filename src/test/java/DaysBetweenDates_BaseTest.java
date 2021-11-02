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

public class DaysBetweenDates_BaseTest {

    protected Model model = null;
    protected OntModel inf = null;
    Reasoner reasoner = null;

    protected static String BASE_URL = "http://www.semanticweb.org/problem-ontology";
    protected static String ONTOLOGY_FILE = "ProblemOntology.owl";
    protected static String DATA_DIRECTION_RULES = "rules/data_direction.rules";

    protected static String DATA_TRANSFER_METHOD_RETURN = "return";
    protected static String DATA_TRANSFER_METHOD_READ_ONLY = "read-only";
    protected static String DATA_TRANSFER_METHOD_WRITE_ONLY = "write-only";
    protected static String DATA_TRANSFER_METHOD_READ_WRITE = "read-write";



    protected Individual daysCount = null;
    protected Individual firstDate_DataElement = null;
    protected Individual secondDate_DataElement = null;

    protected Individual date_Entity = null;

    protected void createModel(String rulesFile)
    {
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
        
        InputStream stream = getClass().getClassLoader().getResourceAsStream(rulesFile);
        String rules = readStream( stream);
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
        firstDate_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "датой рождения человека");

        firstDate_DataElement = inf.createIndividual(BASE_URL + "#DataElement_FirstDate", inf.createOntResource(BASE_URL+"#DataElement"));
        firstDate_DataElement.addRDFType(OWL2.NamedIndividual);
        firstDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "дата рождения");
        firstDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#mission"), "дата рождения");

        firstDate_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#describe"), firstDate_DataElement);

        //Фраза "и"
        Individual and_phrase = inf.createIndividual(BASE_URL+"#Phrase_And", inf.getOntClass(BASE_URL+"#Phrase"));
        and_phrase.addRDFType(OWL2.NamedIndividual);
        and_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "и");

        //Фраза "второй датой"
        Individual secondDate_phrase = inf.createIndividual(BASE_URL+"#Phrase_SecondDate", inf.getOntClass(BASE_URL+"#Phrase"));
        secondDate_phrase.addRDFType(OWL2.NamedIndividual);
        secondDate_phrase.addProperty(inf.createDatatypeProperty(BASE_URL+"#text"), "датой первого дня в школе");

        secondDate_DataElement = inf.createIndividual(BASE_URL + "#DataElement_SecondDate", inf.createOntResource(BASE_URL+"#DataElement"));
        secondDate_DataElement.addRDFType(OWL2.NamedIndividual);
        secondDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#name"), "дата первого дня в школе");
        secondDate_DataElement.addProperty(inf.createDatatypeProperty(BASE_URL+"#mission"), "дата первого дня в школе");

        //Соединяем фразы
        daysCount_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#next"), between_phrase);
        between_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#next"), firstDate_phrase);
        firstDate_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#next"), and_phrase);
        and_phrase.addProperty(inf.createObjectProperty(BASE_URL+"#next"), secondDate_phrase);




        //
        //Тип предметной области "Дата"
        date_Entity = inf.createIndividual(BASE_URL + "#Entity_Date", inf.createOntResource(BASE_URL + "#Entity"));
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
        date_Entity.addProperty(inf.createObjectProperty(BASE_URL+"#hasComponent"), dayNumber_Scalar );
        date_Entity.addProperty(inf.createObjectProperty(BASE_URL+"#hasComponent"), monthNumber_Scalar);
        date_Entity.addProperty(inf.createObjectProperty(BASE_URL+"#hasComponent"), yearNumber_Scalar);

        //Типы элементов данных
        firstDate_DataElement.addProperty(inf.createObjectProperty(BASE_URL+"#hasDomainType"), date_Entity);
        secondDate_DataElement.addProperty(inf.createObjectProperty(BASE_URL+"#hasDomainType"), date_Entity);
        daysCount.addProperty(inf.createObjectProperty(BASE_URL+"#hasDomainType"), daysPeriod_Scalar);



        //Сущность самой задачи
        Individual problem = inf.createIndividual(BASE_URL + "#Problem_Individual", inf.createOntResource(BASE_URL + "#Problem"));
        problem.addRDFType(OWL2.NamedIndividual);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasFormulation"), daysCount_phrase);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasInputData"), firstDate_DataElement);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasInputData"), secondDate_DataElement);
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasOutputData"), daysCount);

        //Первая фраза задачи
        problem.addProperty(inf.createObjectProperty(BASE_URL+"#hasFormulation"), daysCount_phrase);
    }



    protected static String readStream(InputStream is) {
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
}
