package hotel_Reception;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Krishnadas Charankat baiju
 */
/*
The java jaxb parser class that will enable me to load the xml and be able to save changes back
*/



 class JAXB_XMLParser
    {
        private String xml;
      
        JAXBContext jaxbContext;
        
        JAXB_XMLParser(String xmlPath,Class packagename) throws JAXBException
        {
            this.xml=xmlPath;
            jaxbContext= JAXBContext.newInstance(packagename);
        }
        
        
        // the main function
        
       Object  unMarshaller() throws JAXBException
       {
          Unmarshaller unmarshaller=   this.jaxbContext.createUnmarshaller();
            Object unmarshal = unmarshaller.unmarshal(new File(xml));
          return unmarshal; 
       }
        
      void marshaller(Object obj) throws JAXBException
      {
        Marshaller marshaller=  this.jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(obj, new File(xml));
      
      }
    }