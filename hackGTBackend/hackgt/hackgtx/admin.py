from django.contrib import admin
from .models import SharedWith, Image, User, Music, Friend

# Register your models here.

admin.site.register(SharedWith)
admin.site.register(Image)
admin.site.register(Music)
admin.site.register(Friend)
admin.site.register(User)
