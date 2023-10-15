from django.db import models

# Create your models here.


class User(models.Model):

    user_id = models.CharField(max_length=30, primary_key=True)

    def to_dict(self):

        return {'user_id' : self.user_id}


class Music(models.Model):

    track_id = models.IntegerField(primary_key=True)
    link = models.CharField(max_length=100)

    def to_dict(self):

        return {'track_id' : self.track_id,
                'link' : self.link}


class Image(models.Model):

    image_id = models.IntegerField(primary_key=True)
    x = models.FloatField()
    y = models.FloatField()
    creator = models.ForeignKey(User, on_delete=models.CASCADE)
    track = models.ForeignKey(Music, on_delete=models.CASCADE)
    path = models.CharField(max_length=100)

    def to_dict(self):

        return {'image_id' : self.image_id,
                'x' : self.x,
                'y' : self.y,
                'creator' : self.creator.to_dict(),
                'track' : self.track.to_dict(),
                'path' : self.path}


class SharedWith(models.Model):

    share_id = models.IntegerField(primary_key=True)
    shared_with = models.ForeignKey(User, on_delete=models.CASCADE)
    locked = models.BooleanField()
    image_id = models.ForeignKey(Image, on_delete=models.CASCADE)

    def to_dict(self):

        return {'shared_id': self.share_id,
                'shared_with': self.shared_with.to_dict(),
                'locked': self.locked,
                'image_id': self.image_id.to_dict()}


class Friend(models.Model):

    friendship_id = models.IntegerField(primary_key=True)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE, related_name='source_id')
    friend_id = models.ForeignKey(User, on_delete=models.CASCADE, related_name='friend_id')




