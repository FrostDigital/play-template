<ul class="pills">
    <li ${_sel == 'list' ? 'class="active"' : ''}><a href="@{Users.list}">&{'show-all'}</a></li>
    <li ${_sel == 'create' ? 'class="active"' : ''}><a href="@{Users.create}">&{'user.create'}</a></li>
    <li ${_sel == 'invite' ? 'class="active"' : ''}><a href="@{Users.invite}">&{'user.invite'}</a></li>
</ul>