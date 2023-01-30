package pdl.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final ImageDao imageDao;

  @Autowired
  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) {

    try{
      Optional<Image> img = imageDao.retrieve(id);
      return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(img.get().getData());
    }



  catch(Exception e){
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {

    try{
    var img = imageDao.retrieve(id);
      imageDao.delete(img.get());
      return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(img.get().getData());
    }
  catch(Exception e){
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) {


        try{
            
          Image img = new Image(file.getOriginalFilename(),file.getBytes());
          if(!Objects.equals(file.getContentType() ,MediaType.IMAGE_JPEG_VALUE)){
            return new ResponseEntity<>("unsupported", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
          }
          imageDao.create(img);
          return new ResponseEntity<>("reussis", HttpStatus.CREATED);
        }

        catch(IOException e ) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {


    ArrayNode nodes = mapper.createArrayNode();
    
  try{
    List<Image> liste = imageDao.retrieveAll();

    for(int i = 0 ; i<liste.size() ; i++){
      ObjectNode noeud = mapper.createObjectNode();
      noeud.put("name", liste.get(i).getName());
      noeud.put("id" , liste.get(i).getId());
      nodes.add(noeud);
    }
    return nodes;
  }
catch(Exception e){
  return nodes;
 }
}

}
