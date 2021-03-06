package uk.co.brotherlogic.mdbweb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.jarpur.TemplatePage;
import uk.co.brotherlogic.mdb.Connect;
import uk.co.brotherlogic.mdb.record.Record;
import uk.co.brotherlogic.mdb.record.RecordUtils;

public class Default extends TemplatePage
{
   @Override
   public Class generates()
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public String linkParams(Object arg0)
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected Map<String, Object> convertParams(List<String> elems, Map<String, String> params)
   {

      Map<String, Object> paramMap = new TreeMap<String, Object>();
      try
      {
         Record recToListenTo = RecordUtils.getRecordToListenTo(new String[]
         { "12" });
         Record cdToListenTo = RecordUtils.getRecordToListenTo(new String[]
         { "CD" });
         Record sevenToListenTo = RecordUtils.getRecordToListenTo(new String[]
         { "7" });

         paramMap.put("cd", cdToListenTo);
         paramMap.put("seven", sevenToListenTo);
         paramMap.put("record", recToListenTo);
         paramMap.put("rip", RecordUtils.getRecordToRip(1).get(0));
         paramMap.put("coreversion", Connect.getConnection().getVersionString());

      }
      catch (SQLException e)
      {
         e.printStackTrace();
      }

      return paramMap;
   }

   public boolean tester()
   {
      return false;
   }
}
