import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class FileCabinetTest {

    private FileCabinet fileCabinet;

    @BeforeEach
    void listSetUp(){
        List<Folder> testList = new ArrayList<>();
        List<Folder> multiFolderList = new ArrayList<>();


        testList.add(new FolderImpl("Documents", "MEDIUM"));
        testList.add(new FolderImpl("Movies", "LARGE"));

        multiFolderList.add(new FolderImpl("Photos","MEDIUM"));
        multiFolderList.add(new FolderImpl("Posters", "SMALL"));
        multiFolderList.add(new FolderImpl("JuniorJavaDevelopersInTheNeighborhood", "LARGE"));
        multiFolderList.add(new FolderImpl("IAmOutOfNames", "SMALL"));

        testList.add(new MultiFolderImpl("Music","LARGE",multiFolderList));

        testList.add(new FolderImpl("BadName","MEDIUM"));

        fileCabinet = new FileCabinet(testList);
    }

    @Test
    void findByName(){
        Optional<Folder> folder = fileCabinet.findFolderByName("Documents");
        assertTrue(folder.isPresent());
        assertEquals("Documents", folder.get().getName());
    }
    @Test
    void findByNameInMultiFolder(){
        Optional<Folder> folder = fileCabinet.findFolderByName("Posters");
        assertTrue(folder.isPresent());
        assertEquals("Posters", folder.get().getName());
    }
    @Test
    void findBySizeLarge(){
        List<Folder> sizeList = fileCabinet.findFoldersBySize("LARGE");
        assertEquals(2, sizeList.size());
    }
    @Test
    void findBySizeMedium(){
        List<Folder> sizeList = fileCabinet.findFoldersBySize("MEDIUM");
        assertEquals(3, sizeList.size());
    }
    @Test
    void findBySizeSmall(){
        List<Folder> sizeList = fileCabinet.findFoldersBySize("SMALL");
        assertEquals(2, sizeList.size());
    }

    @Test
    void countAllFolders(){
        int count = fileCabinet.count();
        assertEquals(7, count);
    }

}