package vn.greenglobal.tttp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.DocumentMetaData;
import vn.greenglobal.tttp.util.RestServieResourceFacade;

@RestController
@Api(value = "document", description = "File upload")
public class FileUploadController extends TttpController<DocumentMetaData>{

	public FileUploadController(BaseRepository<DocumentMetaData, ?> repo) {
		super(repo);
	}

	@Autowired
    RestServieResourceFacade restServieResourceFacade;

    @RequestMapping(value = "/documents/upload", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<?> upload(
    		@RequestHeader(value = "Authorization", required = true) String authorization,
    		@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            restServieResourceFacade.upload(file);
            Resource fileResource = restServieResourceFacade.download(file.getOriginalFilename());
            return new ResponseEntity<>(fileResource.getURL(), new HttpHeaders(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(
    		@RequestHeader(value = "filename", required = false) String name) {
    	System.out.println("name:"+name);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
    
    //@ResponseBody
    @RequestMapping(value = "/documents/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Object> serveFile(
    		@RequestHeader(value = "Authorization", required = true) String authorization,
    		@PathVariable String filename) throws IOException {
        Resource fileResource = restServieResourceFacade.download(filename);//storageService.loadAsResource(filename);
        System.out.println("uri:"+fileResource.getURI());
        System.out.println("url:"+fileResource.getURL());
        File file = new File(fileResource.getURI());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileResource.getURI()+"\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileResource.contentLength()))
                .build();
    }
    
	/*private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }*/
    
}
