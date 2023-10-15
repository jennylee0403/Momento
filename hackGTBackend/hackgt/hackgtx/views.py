from django.shortcuts import render
from django.http import HttpResponse, HttpResponseNotFound, HttpResponseBadRequest
from .models import SharedWith, Image, User, Music, Friend
from django.db.models import Q
from django.views.decorators.csrf import csrf_exempt
import json
# Create your views here.


def get_images(request):

    if request.method == "GET":

        user = User.objects.get(user_id=request.GET["user_id"])

        response = {'my_images':[], 'other_images':[]}

        images = Image.objects.filter(creator=user)

        shared = SharedWith.objects.filter(shared_with=user)

        for img in images:

            response['my_images'].append(img.to_dict())

        for share in shared:

            response['other_images'].append(share.to_dict())

        return HttpResponse(json.dumps(response))

    else:

        return HttpResponseBadRequest("BAD REQUEST")

def get_friends(request):

    if request.method == "GET":

        user = User.objects.get(user_id=request.GET["user_id"])

        friends = Friend.objects.filter(Q(user_id=user))

        response = {'friends':[]}

        for friend in friends:

            response['friends'].append(friend.friend_id.to_dict())

        return HttpResponse(json.dumps(response))

    else:

        return HttpResponseBadRequest("BAD REQUEST")

@csrf_exempt
def unlock_memory(request):

    if request.method == "GET":

        share_id = request.GET['share_id']

        shared = SharedWith.objects.get(share_id=share_id)

        shared.locked = False

        shared.save()

        shared = SharedWith.objects.get(share_id=share_id)

        return HttpResponse(json.dumps(shared.to_dict()))

    else:

        return HttpResponseBadRequest("BAD")

#Upload memory
#Replace memory
#Unlock memory