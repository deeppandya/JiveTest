package mainpackage.jivetest.support;

import mainpackage.jivetest.model.FlickerPhotos;

public class Utils {

    public static final String FLICKERLINK ="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=b3380a67070b4cb848414a17c9b58433&format=json&nojsoncallback=1" ;
    public static final String FLICKERRECENTPHOTOSLINK ="https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=b3380a67070b4cb848414a17c9b58433&format=json&nojsoncallback=1" ;
    public static final String FLICKERPHOTO="https://www.flickr.com/photos/";

    public static String getFlickerPhotoUrl(FlickerPhotos flickerPhotos){
        return "https://farm"+flickerPhotos.getFarm()+".staticflickr.com/"+flickerPhotos.getServer()+"/"+flickerPhotos.getId()+"_"+flickerPhotos.getSecret()+".jpg";
    }

    public static String getUserUrl(String userId){
        return "https://api.flickr.com/services/rest/?method=flickr.people.getInfo&api_key=b3380a67070b4cb848414a17c9b58433&user_id="+userId+"&format=json&nojsoncallback=1";
    }

    public static String getPhotoComments(String photoId){
        return "https://api.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=b3380a67070b4cb848414a17c9b58433&photo_id="+photoId+"&format=json&nojsoncallback=1";
    }

}
