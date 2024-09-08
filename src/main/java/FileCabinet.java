import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    /**
     *W rozwiązaniu każdy folder sprawdzany jest po kolei
     *Jeśli w większości przypadków poszukiwany folder znajdowałby się poza
     *multifolderem optymalnym rozwiązaniem może być sprawdzenie folderów w pierwszej kolejności
     * np poprzez folders.stream().filter(folder->folder.getName().equals(name).findFirst()
     */
    @Override
    public Optional<Folder> findFolderByName(String name){
        return findFolderByName(name, folders);
    }

    /**
     *Zaimplementowane rozwiązanie jest case-sensitive
     *Można rozważyć utworzenie Enuma zawierającego rozmiary
     */
    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> result = new ArrayList<>();
        findFoldersBySize(size, folders, result);
        return result;
    }

    /**
     * W rozwiązaniu założyłem, że chociaż Multifolder może posiadać nazwę oraz rozmiar
     * to nie będzie możliwe wyszukanie go po nazwie, rozmiarze ani zliczenie do ogólnej ilości
     * folderów. Napewno doprecyzowałbym to przed implementacją
     */
    @Override
    public int count() {
        return countAllFolders(folders);
    }

    private Optional<Folder> findFolderByName(String name, List<Folder> folders) {

        for (Folder folder:folders) {
            if(folder instanceof MultiFolder){
                Optional<Folder> found = findFolderByName(name,((MultiFolder) folder).getFolders());
                if (found.isPresent()){
                    return found;
                }
            } else {
                if(folder.getName().equals(name)) return Optional.of(folder);
            }
        }
        return Optional.empty();
    }

    private void findFoldersBySize(String size, List<Folder> folders, List<Folder> result) {
        for (Folder folder : folders) {
            if (folder instanceof MultiFolder) {
                findFoldersBySize(size, ((MultiFolder) folder).getFolders(), result);
            } else {
                if (folder.getSize().equals(size)) {
                    result.add(folder);
                }
            }
        }
    }

    public int countAllFolders(List<Folder> folders) {
        int count = 0;
        for (Folder folder : folders) {

            if (folder instanceof MultiFolder) {
                count += countAllFolders(((MultiFolder) folder).getFolders());
            }else{
                count++;
            }
        }
        return count;
    }
}